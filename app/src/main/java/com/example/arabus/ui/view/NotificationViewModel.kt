package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.core.domain.notification.NotificationDomain
import com.example.arabus.core.request.NotificationRequest
import com.example.arabus.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(application: Application) : AndroidViewModel(application) {
    private val notificationRepository = NotificationRepository()

    fun getNotificationsByUserId(notificationRequest: NotificationRequest, onResult: (List<NotificationDomain>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val notifications = notificationRepository.getNotificationsByUserId(notificationRequest)
            onResult(notifications)
        }
    }
}
