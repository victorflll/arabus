package com.example.arabus.repository

import com.example.arabus.core.interfaces.IUserApi
import com.example.arabus.core.interfaces.IUserRepository
import com.example.arabus.core.network.RetrofitInstance
import com.example.arabus.core.request.UserRequest
import com.example.arabus.core.response.UserResponse
import com.example.arabus.repository.internal.entities.User
import java.util.UUID

class UserRepository : IUserRepository {
    private val api = RetrofitInstance.user

    override suspend fun getUser(): User {
        val response: List<UserResponse> = api.getUser()
        return response.map { it.toModel() }
    }

    override suspend fun createUser(user: UserRequest): UUID {
        TODO("Not yet implemented")
    }
}

