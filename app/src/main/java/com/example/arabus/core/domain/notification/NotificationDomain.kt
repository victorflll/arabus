package com.example.arabus.core.domain.notification

import java.time.LocalDateTime
import java.util.UUID

data class NotificationDomain(
    val id: UUID,
    val userId: UUID,
    val title: String,
    val message: String,
    val timestamp: LocalDateTime
)
