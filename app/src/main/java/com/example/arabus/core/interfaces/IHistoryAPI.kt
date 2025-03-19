package com.example.arabus.core.interfaces

import com.example.arabus.core.response.HistoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IHistoryAPI {
    @GET("history")
    suspend fun getHistory(): Response<List<HistoryResponse>>

    @GET("history/{user_id}")
    suspend fun getHistoryByUserId(@Path("user_id") userId: String): Response<List<HistoryResponse>>
}