package com.example.arabus.core.interfaces

import com.example.arabus.core.domain.notification.NotificationDomain
import com.example.arabus.core.request.NotificationRequest

interface INotificationRepository {
    suspend fun getNotifications(): List<NotificationDomain>

    suspend fun getNotificationsByUserId(notificationRequest: NotificationRequest): List<NotificationDomain>
}
