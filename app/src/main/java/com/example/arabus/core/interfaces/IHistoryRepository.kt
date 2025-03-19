package com.example.arabus.core.interfaces

import com.example.arabus.core.domain.history.History
import com.example.arabus.core.request.HistoryRequest


interface IHistoryRepository {
    suspend fun getHistory(): List<History>

    suspend fun getHistoryById(historyRequest: HistoryRequest): List<History>
}