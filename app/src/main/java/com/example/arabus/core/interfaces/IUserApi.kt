package com.example.arabus.core.interfaces

import com.example.arabus.core.request.UserRequest
import com.example.arabus.core.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IUserApi {
    @GET("user")
    suspend fun getUser(): UserResponse?

    @POST("user")
    suspend fun createUser(@Body user: UserRequest): UserResponse
}