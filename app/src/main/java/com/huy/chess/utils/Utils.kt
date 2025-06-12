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
import com.huy.chess.ui.component.fenOtherPart
import java.security.KeyStore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import com.huy.chess.R
import kotlin.math.ceil

object Utils {

    fun fenToBoard(fen: String): Pair<List<MutableList<Piece>>, Boolean> {
        var list: List<MutableList<Piece>> = List(8) { mutableListOf() }
        if(fen.isNullOrEmpty()) return list to true
        val strings = fen.split(" ")
        var rank = 0
        var file = 0
        strings[0].forEach {
            if(it != '/') {
                if (it.isDigit()) {
                    for (i in 0..(it - '1')) {
                        list[rank].add(Piece(rank, file, ' '))
                        file++
                    }
                } else {
                    list[rank].add(Piece(rank, file, it))
                    file++
                }
            }
            else {
                rank++
                file = 0
            }
        }
        return list to (strings[1] == "w")
    }

    fun boardToFen(board: List<List<Piece>>) : String {
        var fen = ""
        board.forEachIndexed { index, list ->
            var emptyCount = 0
            list.forEach {
                if(it.piece == ' ') {
                    emptyCount++
                } else {
                    if(emptyCount != 0) {
                        fen += emptyCount
                        emptyCount = 0
                    }
                    fen += it.piece
                }
            }
            if(emptyCount != 0) fen += emptyCount
            if(index != 7) fen += '/'
        }
        fen += fenOtherPart()
        return fen
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
        if(input.size < 5) return ""
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

    fun getTimeControlDrawableRes(timeControl: String): Int {
        return when (timeControl) {
            "60+0" -> R.drawable.bullet
            "60+1" -> R.drawable.bullet
            "120+1" -> R.drawable.bullet
            "180+0" -> R.drawable.blitz
            "180+2" -> R.drawable.blitz
            "300+0" -> R.drawable.blitz
            "300+5" -> R.drawable.blitz
            "300+2" -> R.drawable.blitz
            "600+0" -> R.drawable.rapid
            "600+5" -> R.drawable.rapid
            "900+10" -> R.drawable.rapid
            "1200+0" -> R.drawable.rapid
            "1800+0" -> R.drawable.rapid
            "3600+0" -> R.drawable.rapid
            "86400+0" -> R.drawable.daily
            "172800+0" -> R.drawable.daily
            "259200+0" -> R.drawable.daily
            "432000+0" -> R.drawable.daily
            "604800+0" -> R.drawable.daily
            "1209600+0" -> R.drawable.daily
            "∞" -> R.drawable.rapid
            else -> R.drawable.rapid
        }
    }

    fun getMoveCount(moves: Int) = ceil(moves / 2f).toInt()
}