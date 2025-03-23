package com.example.arabus.core.interfaces

import com.example.arabus.core.request.FavoriteRequest

import com.example.arabus.core.response.FavoriteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface IFavoriteAPI {
    @GET("favorite")
    suspend fun getFavorites(): Response<List<FavoriteResponse>>

    @GET("favorite/{user_id}")
    suspend fun getFavoritesByUserId(@Path("user_id") userId: String): Response<List<FavoriteResponse>>

    @POST("favorite")
    suspend fun saveFavorite(
        @Body favoriteRequest: FavoriteRequest,
        @Query("user_id") userId: String
    ): Response<Unit>

    @DELETE("favorite/{userId}/{routeId}")
    suspend fun deleteFavorite(
        @Path("userId") userId: String,
        @Path("routeId") routeId: String
    ): Response<Unit>

}
