package com.example.arabus.core.request

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class UserRequest(
    val email: String,
    val password: String,
    @SerializedName("role_id")
    val roleId: UUID,
    val name: String,
    @SerializedName("phone_number")
    val phoneNumber: String
)