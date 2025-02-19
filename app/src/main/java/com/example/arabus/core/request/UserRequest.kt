package com.example.arabus.core.request

import java.util.UUID

data class UserRequest(
    val email: String,
    val password: String,
    val roleId: UUID,
    val name: String,
    val phoneNumber: String
)