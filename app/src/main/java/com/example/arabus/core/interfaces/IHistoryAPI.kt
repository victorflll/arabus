package com.example.arabus.core.interfaces

import com.example.arabus.core.request.HistoryRequest
import com.example.arabus.core.response.HistoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IHistoryAPI {
    @GET("history")
    suspend fun getHistory(): Response<List<HistoryResponse>>

    @GET("history")
    suspend fun getHistoryById(@Query("userId") historyRequest: HistoryRequest): Response<List<HistoryResponse>>
}