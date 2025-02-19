package com.example.arabus.repository

import com.example.arabus.core.domain.user.User
import com.example.arabus.core.interfaces.IUserRepository
import com.example.arabus.core.network.RetrofitInstance
import com.example.arabus.core.request.UserRequest
import com.example.arabus.core.response.UserResponse
import java.util.UUID

class UserRepository : IUserRepository {
    private val api = RetrofitInstance.user

    override suspend fun getUser(): User? {
        val response: UserResponse = api.getUser() ?: return null
        return response.toEntity()
    }

    override suspend fun createUser(user: UserRequest): UUID {
        val response = api.createUser(user)
        return response.id
    }
}

