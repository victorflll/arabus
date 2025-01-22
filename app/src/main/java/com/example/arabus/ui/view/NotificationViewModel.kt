package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.Notification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseInstance.getDatabase(application)
    private val notificationDao = database.notificationDao()

    fun insertNotification(userId: Int, title: String, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val notification = Notification(
                userId = userId,
                title = title,
                message = message,
                timestamp = java.util.Date()
            )
            notificationDao.insert(notification)
        }
    }

    fun getNotificationsByUserId(userId: Int, onResult: (List<Notification>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val notifications = notificationDao.getByUserId(userId)
            onResult(notifications)
        }
    }

    fun updateNotification(notificationId: Int, newTitle: String?, newMessage: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingNotification = notificationDao.getById(notificationId)
            if (existingNotification != null) {
                val updatedNotification = existingNotification.copy(
                    title = newTitle ?: existingNotification.title,
                    message = newMessage ?: existingNotification.message
                )
                notificationDao.update(updatedNotification)
            }
        }
    }

    fun deleteNotification(notificationId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val notificationToDelete = notificationDao.getById(notificationId)
            if (notificationToDelete != null) {
                notificationDao.delete(notificationToDelete)
            }
        }
    }
}
