package com.example.arabus.core.response

import com.example.arabus.core.dtos.UserDto

data class UserResponse(
    val id: String,
    val email: String,
    val password: String
) {
    fun toModel(): UserDto {
        return UserDto(id = this.id, email = this.email)
    }
}
