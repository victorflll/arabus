package com.example.arabus.core.interfaces

import com.example.arabus.core.response.FavoriteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IFavoriteAPI {
    @GET("favorites")
    suspend fun getFavorites(): Response<List<FavoriteResponse>>

    @GET("favorites")
    suspend fun getFavoritesByUserId(@Query("userId") userId: String): Response<List<FavoriteResponse>>
}
