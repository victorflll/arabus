package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun getHistoryByUserId(userId: Int, onResult: (List<History>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val historyList = historyDao.getByUserId(userId)
            onResult(historyList)
        }
    }

    fun getHistoryByRouteId(routeId: Int, onResult: (List<History>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val historyList = historyDao.getByRouteId(routeId)
            onResult(historyList)
        }
    }
}
