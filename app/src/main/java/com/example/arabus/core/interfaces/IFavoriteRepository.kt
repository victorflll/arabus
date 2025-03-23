package com.example.arabus.core.interfaces

import com.example.arabus.core.request.FavoriteRequest
import com.example.arabus.core.domain.favorite.FavoriteDomain

interface IFavoriteRepository {
    suspend fun getFavorites(): List<FavoriteDomain>
    suspend fun getFavoritesByUserId(favoriteRequest: FavoriteRequest): List<FavoriteDomain>
    suspend fun saveFavorite(favoriteRequest: FavoriteRequest)
}
