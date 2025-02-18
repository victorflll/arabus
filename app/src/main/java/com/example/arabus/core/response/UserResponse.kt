package com.example.arabus.core.response

import com.example.arabus.core.domain.user.User
import java.time.LocalDateTime
import java.util.UUID

data class UserResponse(
    val id: UUID,
    val email: String,
    val token: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val profile: ProfileResponse,
    val role: RoleResponse
) {
    fun toEntity(): User {
        return User(
            this.id,
            this.email,
            this.token,
            this.createdAt,
            this.updatedAt,
            this.profile.toEntity(),
            this.role.toEntity()
        )
    }
}
