package com.huy.chess.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun String.toRequestBody(): RequestBody =
    this.toRequestBody("text/plain".toMediaTypeOrNull())

fun File.toMultipart(partName: String): MultipartBody.Part? {
    val validExtensions = listOf("png", "jpg", "jpeg")
    val fileExtension = this.extension.lowercase()
    if (fileExtension !in validExtensions) {
        return null
    }
    val mimeType = when (fileExtension) {
        "png" -> "image/png"
        "jpg", "jpeg" -> "image/jpeg"
        else -> return null
    }
    val requestFile = this.asRequestBody(mimeType.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, this.name, requestFile)
}

fun Context.uriToFile(uri: Uri): File? {
    val inputStream = contentResolver.openInputStream(uri) ?: return null
    val file = File(cacheDir, "temp_avatar.jpg")
    file.outputStream().use { outputStream ->
        inputStream.copyTo(outputStream)
    }
    return file
}