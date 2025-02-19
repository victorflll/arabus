package com.example.arabus.repository

import com.example.arabus.core.domain.history.History
import com.example.arabus.core.interfaces.IHistoryRepository
import com.example.arabus.core.network.RetrofitInstance
import com.example.arabus.core.request.HistoryRequest

class HistoryRepository : IHistoryRepository {
    private val api = RetrofitInstance.history

    override suspend fun getHistory(): List<History> {
        try {
            val response = api.getHistory()

            if (response.isSuccessful) {
                return response.body()?.map { it.toEntity() } ?: emptyList()
            } else {
                val errorCode = response.code()
                val errorBody = response.errorBody()?.string()
                throw Exception("Erro de API: Código $errorCode - $errorBody")
            }
        } catch (e: Exception) {
            throw Exception("Erro ao buscar histórico: ${e.message}")
        }
    }

    override suspend fun getHistoryById(historyRequest: HistoryRequest): List<History> {
        TODO("Not yet implemented")
    }


}
