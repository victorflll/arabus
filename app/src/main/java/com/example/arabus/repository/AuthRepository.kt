package com.example.arabus.repository

import com.example.arabus.core.interfaces.IAuthRepository
import com.example.arabus.core.network.RetrofitInstance
import com.example.arabus.core.request.LoginRequest

class AuthRepository : IAuthRepository {
    private val api = RetrofitInstance.auth

    override suspend fun login(loginRequest: LoginRequest): String {
        val response = api.login(loginRequest)
        return response.token
    }
}