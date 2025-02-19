package com.example.arabus.core.response

import com.example.arabus.core.domain.user.User
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.UUID

data class UserResponse(
    val id: UUID,
    val email: String,
    val token: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val profile: ProfileResponse,
    val role: RoleResponse
) {
    fun toEntity(): User {
        return User(
            this.id,
            this.email,
            this.token,
            LocalDateTime.parse(this.createdAt),
            LocalDateTime.parse(this.updatedAt),
            this.profile.toEntity(),
            this.role.toEntity()
        )
    }
}
