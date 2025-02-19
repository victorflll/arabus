package com.example.arabus.core.interfaces

import com.example.arabus.core.response.NotificationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface INotificationAPI {
    @GET("notifications")
    suspend fun getNotifications(): Response<List<NotificationResponse>>

    @GET("notifications")
    suspend fun getNotificationsByUserId(@Query("userId") userId: String): Response<List<NotificationResponse>>
}
