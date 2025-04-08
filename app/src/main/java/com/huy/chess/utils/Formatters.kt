package com.huy.chess.utils

import com.huy.chess.utils.enums.TimeType
import java.util.concurrent.TimeUnit

object Formatters {

    fun changeTimeToMillis(time: TimeType): Long {
        val secondTime : Long = when(time) {
            TimeType.ONE_MINUTE, TimeType.ONE_MINUTE_PLUS_ONE -> 60
            TimeType.TWO_MINUTES_PLUS_ONE -> 120
            TimeType.THREE_MINUTES, TimeType.THREE_MINUTES_PLUS_TWO -> 180
            TimeType.FIVE_MINUTES, TimeType.FIVE_MINUTES_PLUS_FIVE, TimeType.FIVE_MINUTES_PLUS_TWO -> 300
            TimeType.TEN_MINUTES, TimeType.TEN_MINUTES_PLUS_FIVE -> 600
            TimeType.FIFTEEN_MINUTES_PLUS_TEN -> 900
            TimeType.TWENTY_MINUTES -> 1200
            TimeType.THIRTY_MINUTES -> 1800
            TimeType.SIXTY_MINUTES -> 3600
            TimeType.ONE_DAY -> 86400
            TimeType.TWO_DAYS -> 2 * 86400
            TimeType.THREE_DAYS -> 3 * 86400
            TimeType.FIVE_DAYS -> 5 * 86400
            TimeType.SEVEN_DAYS -> 7 * 86400
            TimeType.FOURTEEN_DAYS -> 14 * 86400
            TimeType.UNLIMITED -> 0

        }
        return secondTime * 1000
    }

    fun formatSmartTime(millis: Long): String {
        val days = TimeUnit.MILLISECONDS.toDays(millis)
        val hours = TimeUnit.MILLISECONDS.toHours(millis) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60

        return when {
            days > 0 -> "$days:"
            hours > 0 -> "$hours:"
            minutes >= 0 -> "$minutes:"
            seconds >= 0 -> "$seconds"
            else -> ""
        }
    }
}