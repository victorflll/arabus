package com.example.arabus.core.interfaces

import com.example.arabus.core.request.LoginRequest
import com.example.arabus.core.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthApi {
    @POST("auth")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}