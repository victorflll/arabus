package com.example.arabus.core.interfaces

import com.example.arabus.core.request.LoginRequest

interface IAuthRepository {
    suspend fun login(loginRequest: LoginRequest): String
}