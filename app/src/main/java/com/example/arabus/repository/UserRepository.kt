package com.example.arabus.repository

import com.example.arabus.core.dtos.UserDto
import com.example.arabus.core.network.RetrofitInstance
import com.example.arabus.core.response.UserResponse

class UserRepository {
    private val api = RetrofitInstance.userApi

    suspend fun getUsers(): List<UserDto> {
        val response: List<UserResponse> = api.getAllUsers()
        return response.map { it.toModel() }
    }
}

