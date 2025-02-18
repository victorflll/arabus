package com.example.arabus.core.interfaces

interface IAuthRepository {
    suspend fun login(loginRequest: LoginRequest): String
}