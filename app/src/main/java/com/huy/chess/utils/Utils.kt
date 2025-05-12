package com.huy.chess.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.huy.chess.data.model.Piece
import java.security.KeyStore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object Utils {

    fun fenToBoard(fen: String): Pair<List<MutableList<Piece>>, Boolean> {
        var list: List<MutableList<Piece>> = List(8) { mutableListOf() }
        val strings = fen.split(" ")
        var rank = 0
        var file = 0
        strings[0].forEach {
            if(it != '/') {
                if (it.isDigit()) {
                    for (i in 0..(it - '1')) {
                        file += i
                        list[rank].add(Piece(rank, file, ' '))
                    }
                } else {
                    list[rank].add(Piece(rank, file, it))
                }
                file++
            }
            else {
                rank++
                file = 0
            }
        }
        return list to (strings[1] == "w")
    }

    fun initBoard(): List<MutableList<Piece>> {
        return List(8) { row ->
            mutableStateListOf(*Array(8) { col -> getInitialPiece(row, col) })
        }
    }
    private fun getInitialPiece(row: Int, col: Int): Piece {
        return Piece(row, col, when (row) {
            0 -> {
                when (col) {
                    0, 7 -> 'r'
                    1, 6 -> 'n'
                    2, 5 -> 'b'
                    3 -> 'q'
                    4 -> 'k'
                    else -> ' '
                }
            }
            1 -> 'p'
            6 -> 'P'
            7 -> when(col) {
                0, 7 -> 'R'
                1, 6 -> 'N'
                2, 5 -> 'B'
                3 -> 'Q'
                4 -> 'K'
                else -> ' '
            }
            else -> ' '
        })
    }

    fun loadImageBimap(context: Context, @DrawableRes resId: Int): ImageBitmap {
        val bitmap = BitmapFactory.decodeResource(context.resources, resId)
        return bitmap.asImageBitmap()
    }

    fun loadImageBimap(context: Context, @DrawableRes resId: Int, size: Int): ImageBitmap {
        val option = BitmapFactory.Options().apply {
            inSampleSize = 2
        }
        val bitmap = BitmapFactory.decodeResource(context.resources, resId, option)
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true)
        return scaledBitmap.asImageBitmap()
    }

    fun loadBitmap(context: Context, @DrawableRes resId: Int): Bitmap {
        val option = BitmapFactory.Options().apply {
            inSampleSize = 4
        }
        return BitmapFactory.decodeResource(context.resources, resId, option)
    }

    fun generateKey(alias: String) : SecretKey {
        val kg: KeyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            Constants.KEY_STORE_PROVIDER
        )
        val parameterSpec: KeyGenParameterSpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).run {
            setKeySize(128)
            setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            setRandomizedEncryptionRequired(true)
            build()
        }
        kg.init(parameterSpec)
        return kg.generateKey()
    }

    fun getKey(alias: String) : SecretKey {
        val keyStore = KeyStore.getInstance(Constants.KEY_STORE_PROVIDER).apply { load(null) }
        val entry = keyStore.getEntry(alias, null)
        return if(entry is KeyStore.SecretKeyEntry) {
            entry.secretKey
        } else {
            generateKey(alias)
        }
    }

    fun encodeAESCBC(plain: String, alias: String) : ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, getKey(alias))
        val iv = cipher.iv
        val encoded = cipher.doFinal(plain.toByteArray())
        return iv + encoded
    }

    fun decodeAESCBC(input: ByteArray, alias: String) : String {
        val iv: ByteArray = input.copyOfRange(0, 16)
        val encoded: ByteArray = input.copyOfRange(16, input.size)
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, getKey(alias), ivSpec)
        return cipher.doFinal(encoded).decodeToString()
    }

    fun getToday(): String {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return today.format(formatter)
    }
}