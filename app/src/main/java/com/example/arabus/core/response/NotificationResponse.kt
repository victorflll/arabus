package com.example.arabus.core.response

import com.example.arabus.core.domain.notification.NotificationDomain
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.UUID

data class NotificationResponse(
    val id: UUID,
    @SerializedName("user_id")
    val userId: UUID,
    val title: String,
    val message: String,
    @SerializedName("timestamp")
    val timestamp: String
) {
    fun toDomain(): NotificationDomain {
        return NotificationDomain(
            id = this.id,
            userId = this.userId,
            title = this.title,
            message = this.message,
            timestamp = LocalDateTime.parse(this.timestamp)
        )
    }
}
