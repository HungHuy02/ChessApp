package com.huy.chess.utils

import android.content.Context
import android.net.Uri
import com.huy.chess.utils.enums.TimeType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import com.huy.chess.R
import com.huy.chess.utils.enums.GameResult
import com.huy.chess.utils.enums.GameResultInfo
import com.huy.chess.utils.enums.StockfishBotLevel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONObject

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

fun TimeType.toName() : Int {
    return when(this) {
        TimeType.ONE_MINUTE -> R.string.one_minute_text
        TimeType.ONE_MINUTE_PLUS_ONE -> R.string.one_minute_plus_one_text
        TimeType.TWO_MINUTES_PLUS_ONE -> R.string.two_minute_plus_one_text
        TimeType.THREE_MINUTES -> R.string.three_minute_text
        TimeType.THREE_MINUTES_PLUS_TWO -> R.string.three_minute_plus_two_text
        TimeType.FIVE_MINUTES -> R.string.five_minute_text
        TimeType.FIVE_MINUTES_PLUS_FIVE -> R.string.five_minute_plus_five_text
        TimeType.FIVE_MINUTES_PLUS_TWO -> R.string.five_min_plus_two
        TimeType.TEN_MINUTES -> R.string.ten_minute_text
        TimeType.TEN_MINUTES_PLUS_FIVE -> R.string.ten_min_plus_5
        TimeType.FIFTEEN_MINUTES_PLUS_TEN -> R.string.fifteen_minute_plus_ten
        TimeType.TWENTY_MINUTES -> R.string.twenty_min
        TimeType.THIRTY_MINUTES -> R.string.thirty_minute
        TimeType.SIXTY_MINUTES -> R.string.sixty_min
        TimeType.ONE_DAY -> R.string.one_day
        TimeType.TWO_DAYS -> R.string.two_days_text
        TimeType.THREE_DAYS -> R.string.three_days_text
        TimeType.FIVE_DAYS -> R.string.five_days_text
        TimeType.SEVEN_DAYS -> R.string.seven_days_text
        TimeType.FOURTEEN_DAYS -> R.string.fourteen_days
        TimeType.UNLIMITED -> R.string.no_time_text
    }
}

fun TimeType.toPair() : Pair<Int, Int> {
    return when(this) {
        TimeType.ONE_MINUTE -> R.drawable.bullet to R.string.one_minute_text
        TimeType.ONE_MINUTE_PLUS_ONE -> R.drawable.bullet to R.string.one_minute_plus_one_text
        TimeType.TWO_MINUTES_PLUS_ONE -> R.drawable.bullet to R.string.two_minute_plus_one_text
        TimeType.THREE_MINUTES ->R.drawable.blitz to R.string.three_minute_text
        TimeType.THREE_MINUTES_PLUS_TWO -> R.drawable.blitz to R.string.three_minute_plus_two_text
        TimeType.FIVE_MINUTES -> R.drawable.blitz to R.string.five_minute_text
        TimeType.FIVE_MINUTES_PLUS_FIVE -> R.drawable.blitz to R.string.five_minute_plus_five_text
        TimeType.FIVE_MINUTES_PLUS_TWO -> R.drawable.blitz to R.string.five_min_plus_two
        TimeType.TEN_MINUTES -> R.drawable.rapid to R.string.ten_minute_text
        TimeType.TEN_MINUTES_PLUS_FIVE -> R.drawable.rapid to R.string.ten_min_plus_5
        TimeType.FIFTEEN_MINUTES_PLUS_TEN -> R.drawable.rapid to R.string.fifteen_minute_plus_ten
        TimeType.TWENTY_MINUTES -> R.drawable.rapid to R.string.twenty_min
        TimeType.THIRTY_MINUTES -> R.drawable.rapid to R.string.thirty_minute
        TimeType.SIXTY_MINUTES -> R.drawable.rapid to R.string.sixty_min
        TimeType.ONE_DAY -> R.drawable.daily to R.string.one_day
        TimeType.TWO_DAYS -> R.drawable.daily to R.string.two_days_text
        TimeType.THREE_DAYS -> R.drawable.daily to R.string.three_days_text
        TimeType.FIVE_DAYS -> R.drawable.daily to R.string.five_days_text
        TimeType.SEVEN_DAYS -> R.drawable.daily to R.string.seven_days_text
        TimeType.FOURTEEN_DAYS -> R.drawable.daily to R.string.fourteen_days
        TimeType.UNLIMITED -> -1 to -1
    }
}

fun GameResult.toResult(): GameResultInfo {
    return when(this) {
        GameResult.WIN_CHECKMATE -> GameResultInfo("1-0", R.string.you_won_text, R.string.checkmate_text)
        GameResult.WIN_OPPONENT_RESIGNED -> GameResultInfo("1-0", R.string.you_won_text, R.string.surrendered_text)
        GameResult.WIN_OPPONENT_FORFEIT -> GameResultInfo("1-0", R.string.you_won_text, R.string.resigned_text)
        GameResult.WIN_TIMEOUT -> GameResultInfo("1-0", R.string.you_won_text, R.string.time_out_text)
        GameResult.LOSE_CHECKMATE -> GameResultInfo("0-1", R.string.white_win_text, R.string.checkmate_text)
        GameResult.LOSE_RESIGNED -> GameResultInfo("0-1", R.string.white_win_text, R.string.surrendered_text)
        GameResult.LOSE_FORFEIT -> GameResultInfo("0-1", R.string.white_win_text, R.string.resigned_text)
        GameResult.LOSE_TIMEOUT -> GameResultInfo("0-1", R.string.white_win_text, R.string.time_out_text)
        GameResult.DRAW_STALEMATE -> GameResultInfo("1/2-1/2", R.string.draw_text, R.string.out_of_move)
        GameResult.DRAW_THREEFOLD_REPETITION -> GameResultInfo("1/2-1/2", R.string.draw_text, R.string.draw_by_repetition_text)
        GameResult.DRAW_FIFTY_MOVE_RULE -> GameResultInfo("1/2-1/2", R.string.draw_text, R.string.fifty_move_rule_text)
        GameResult.DRAW_INSUFFICIENT_MATERIAL -> GameResultInfo("1/2-1/2", R.string.draw_text, R.string.insufficient_material_text)
        GameResult.DRAW_AGREEMENT -> GameResultInfo("1/2-1/2", R.string.draw_text, R.string.agreement_text)
        GameResult.DRAW_TIMEOUT_INSUFFICIENT_MATERIAL -> GameResultInfo("1/2-1/2", R.string.draw_text, R.string.insufficient_material_text)
    }
}

fun StockfishBotLevel.toInt() : Int {
    return when(this) {
        StockfishBotLevel.ONE -> 1
        StockfishBotLevel.TWO -> 2
        StockfishBotLevel.THREE -> 3
        StockfishBotLevel.FOUR -> 4
        StockfishBotLevel.FIVE -> 5
        StockfishBotLevel.SIX -> 6
        StockfishBotLevel.SEVEN -> 7
        StockfishBotLevel.EIGHT -> 8
    }
}

inline fun <reified T> T.toJsonObject(): JSONObject {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val adapter = moshi.adapter(T::class.java)
    val jsonString = adapter.toJson(this)
    return JSONObject(jsonString)
}

fun <K> MutableMap<K, Int>.increment(key: K) {
    this[key] = (this[key] ?: 0) + 1
}