package com.huy.chess.utils

import java.util.concurrent.TimeUnit

object Formatters {

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