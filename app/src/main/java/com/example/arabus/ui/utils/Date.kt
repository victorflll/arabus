package com.example.arabus.ui.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale
import java.util.concurrent.TimeUnit


fun LocalDateTime.toFormattedTime(): String {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(this)
}


fun LocalDateTime.timeDifference(other: LocalDateTime): String {
    val duration = java.time.Duration.between(this, other)
    val minutes = duration.toMinutes() % 60
    return "$minutes min"
}