package com.example.arabus.core.interfaces

import com.example.arabus.core.response.NotificationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface INotificationAPI {
    @GET("notification")
    suspend fun getNotifications(): Response<List<NotificationResponse>>

    @GET("notification/{user_id}")
    suspend fun getNotificationsByUserId(@Path("user_id") userId: String): Response<List<NotificationResponse>>
}
