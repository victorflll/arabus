package com.example.arabus.repository

import com.example.arabus.core.domain.favorite.FavoriteDomain
import com.example.arabus.core.interfaces.IFavoriteRepository
import com.example.arabus.core.network.RetrofitInstance
import com.example.arabus.core.request.FavoriteRequest
import java.util.UUID

class FavoriteRepository : IFavoriteRepository {
    private val api = RetrofitInstance.favorite

    override suspend fun getFavorites(): List<FavoriteDomain> {
        val response = api.getFavorites()
        return response.body()?.map { it.toEntity() } ?: emptyList()
    }

    override suspend fun getFavoritesByUserId(favoriteRequest: FavoriteRequest): List<FavoriteDomain> {
        val response = api.getFavoritesByUserId(favoriteRequest.userId.toString())
        return response.body()?.map { it.toEntity() } ?: emptyList()
    }

    override suspend fun saveFavorite(favoriteRequest: FavoriteRequest) {
        api.saveFavorite(
            favoriteRequest = favoriteRequest,
            userId = favoriteRequest.userId.toString()
        )
    }

    suspend fun deleteFavorite(userId: UUID, routeId: UUID) {
        api.deleteFavorite(userId.toString(), routeId.toString())
    }



}
