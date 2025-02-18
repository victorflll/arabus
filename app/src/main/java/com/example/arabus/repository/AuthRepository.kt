package com.example.arabus.repository

import com.example.arabus.core.interfaces.IAuthRepository

class AuthRepository : IAuthRepository {
    override suspend fun login(loginRequest: LoginRequest): String {
        TODO("Not yet implemented")
    }
}