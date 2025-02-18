package com.example.arabus.core.domain.user

import com.example.arabus.core.domain.profile.Profile
import com.example.arabus.core.domain.role.Role
import java.time.LocalDateTime
import java.util.UUID

data class User(
    val id: UUID,
    val email: String,
    val token: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val profile: Profile,
    val role: Role
)