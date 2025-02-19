package com.example.arabus.core.interfaces

import com.example.arabus.core.response.FavoriteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IFavoriteAPI {
    @GET("favorite")
    suspend fun getFavorites(): Response<List<FavoriteResponse>>

    @GET("favorite/{user_id}")
    suspend fun getFavoritesByUserId(@Path("user_id") userId: String): Response<List<FavoriteResponse>>
}
