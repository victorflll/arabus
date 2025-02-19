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
    @SerializedName("created_at")
    val createdAt: String,
    val read: Boolean
) {
    fun toDomain(): NotificationDomain {
        return NotificationDomain(
            id = this.id,
            userId = this.userId,
            title = this.title,
            message = this.message,
            createdAt = LocalDateTime.parse(this.createdAt),
            read = this.read
        )
    }
}
