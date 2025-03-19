package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.core.domain.history.History
import com.example.arabus.core.request.HistoryRequest
import com.example.arabus.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val historyRepository = HistoryRepository()

    fun getHistoryByUserId(request: HistoryRequest, onResult: (List<History>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val historic = historyRepository.getHistoryById(request)
            onResult(historic)
        }
    }
}
