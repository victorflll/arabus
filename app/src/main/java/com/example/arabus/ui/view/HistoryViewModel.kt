package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseInstance.getDatabase(application)
    private val historyDao = database.historyDao()

    fun insertHistory(userId: Int, routeId: Int, finishedAt: Date? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val history = History(
                userId = userId,
                routeId = routeId,
                finishedAt = finishedAt
            )
            historyDao.insert(history)
        }
    }

    suspend fun getHistoryByUserId(userId: Int): List<History> {
        return withContext(Dispatchers.IO) {
            historyDao.getByUserId(userId)
        }
    }


    suspend fun getHistoryByRouteId(routeId: Int): List<History> {
        return withContext(Dispatchers.IO) {
            historyDao.getByRouteId(routeId)
        }
    }

}
