package com.example.arabus.core.interfaces

import com.example.arabus.core.request.LoginRequest
import com.example.arabus.core.response.LoginResponse
import retrofit2.http.POST

interface IAuthApi {
    @POST("auth")
    suspend fun login(loginRequest: LoginRequest): LoginResponse
}