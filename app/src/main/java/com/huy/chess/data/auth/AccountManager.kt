package com.huy.chess.data.auth

import android.app.Activity
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.huy.chess.R
import com.huy.chess.data.model.SignInResult
import com.huy.chess.data.model.SignUpResult
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AccountManager(
    private val activity: Activity
) {
    private val credentialManager = CredentialManager.create(activity)

    suspend fun signUp(username: String, password: String): SignUpResult {
        return try {
            credentialManager.createCredential(
                context = activity,
                request = CreatePasswordRequest(
                    id = username,
                    password = password
                )
            )
            SignUpResult.Success(username)
        } catch (e: CreateCredentialCancellationException) {
            e.printStackTrace()
            SignUpResult.Cancelled
        } catch(e: CreateCredentialException) {
            e.printStackTrace()
            SignUpResult.Failure
        }
    }

    suspend fun signIn(): SignInResult {
        return try {
            val credentialResponse = credentialManager.getCredential(
                context = activity,
                request = GetCredentialRequest(
                    credentialOptions = listOf(GetPasswordOption())
                )
            )

            val credential = credentialResponse.credential as? PasswordCredential
                ?: return SignInResult.Failure

            // Make login API call here with credential.id and credential.password

            SignInResult.Success(credential.id)
        } catch(e: GetCredentialCancellationException) {
            e.printStackTrace()
            SignInResult.Cancelled
        } catch(e: NoCredentialException) {
            e.printStackTrace()
            SignInResult.NoCredentials
        } catch(e: GetCredentialException) {
            e.printStackTrace()
            SignInResult.Failure
        }
    }

    suspend fun signInGoogle(): SignInResult {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(activity.getString(R.string.web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(
                context = activity,
                request = request
            )
            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val googleIdToken: String = googleIdTokenCredential.idToken

            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)

            val authResult = Firebase.auth.signInWithCredential(firebaseCredential).await()

            val userId = authResult.user?.uid ?: return SignInResult.Failure
            SignInResult.Success(userId)
        } catch (e: GetCredentialCancellationException) {
            e.printStackTrace()
            SignInResult.Cancelled
        } catch (e: GetCredentialException) {
            e.printStackTrace()
            SignInResult.Failure
        } catch (e: Exception) {
            e.printStackTrace()
            SignInResult.Failure
        }
    }

    suspend fun signInFacebook(callbackManager: CallbackManager, loginManager: LoginManager): SignInResult {
        return suspendCoroutine {
            loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    it.resume(SignInResult.Cancelled)
                }

                override fun onError(error: FacebookException) {
                    it.resume(SignInResult.Failure)
                }

                override fun onSuccess(result: LoginResult) {
                    val accessToken = result.accessToken.token
                    val credential = FacebookAuthProvider.getCredential(accessToken)

                    Firebase.auth.signInWithCredential(credential)
                        .addOnSuccessListener { authResult ->
                            val userId = authResult.user?.uid ?: return@addOnSuccessListener it.resume(
                                SignInResult.Failure)
                            it.resume(SignInResult.Success(userId))
                        }
                        .addOnFailureListener { exception ->
                            exception.printStackTrace()
                            it.resume(SignInResult.Failure)
                        }
                }
            })
        }
    }
}

