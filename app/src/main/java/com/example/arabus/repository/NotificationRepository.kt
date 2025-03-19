package com.example.arabus.repository

import com.example.arabus.core.domain.notification.NotificationDomain
import com.example.arabus.core.interfaces.INotificationRepository
import com.example.arabus.core.network.RetrofitInstance
import com.example.arabus.core.request.NotificationRequest

class NotificationRepository : INotificationRepository {
    private val api = RetrofitInstance.notification

    override suspend fun getNotifications(): List<NotificationDomain> {
        val response = api.getNotifications()
        return response.body()?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getNotificationsByUserId(notificationRequest: NotificationRequest): List<NotificationDomain> {
        val response = api.getNotificationsByUserId(notificationRequest.userId.toString())
        return response.body()?.map { it.toDomain() } ?: emptyList()
    }
}
