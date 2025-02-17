package com.example.arabus.application.interfaces.user

import com.example.arabus.core.response.UserResponse
import retrofit2.http.GET

interface UserApiInterface {
    @GET("end/point")
    suspend fun getAllUsers(): List<UserResponse>
}