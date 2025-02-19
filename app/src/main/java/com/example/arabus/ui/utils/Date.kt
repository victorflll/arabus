package com.example.arabus.ui.utils

import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun Date.toFormattedTime(): String {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(this)
}

fun Date.timeDifference(other: Date): String {
    val diffMillis = this.time - other.time
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
    return "${minutes}min"
}

fun LocalDateTime.toFormattedTime(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    return this.format(formatter)
}

fun LocalDateTime.timeDifference(other: LocalDateTime): String {
    val duration = Duration.between(other, this)
    val minutes = duration.toMinutes()
    return "${minutes}min"
}
