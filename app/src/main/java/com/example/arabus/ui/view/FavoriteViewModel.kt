package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseInstance.getDatabase(application)
    private val favoriteDao = database.favoriteDao()

    fun insertFavorite(userId: Int, routeId: Int, description: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val favorite = Favorite(
                userId = userId,
                routeId = routeId,
                description = description,
                createdAt = Date()
            )
            favoriteDao.insert(favorite)
        }
    }

    suspend fun getFavoritesByUserId(userId: Int): List<Favorite> {
        return withContext(Dispatchers.IO) {
            favoriteDao.getByUserId(userId)
        }
    }


    fun updateFavorite(userId: Int, routeId: Int, newRouteId: Int?, description: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingFavorite = favoriteDao.getByUserId(userId)
            val favoriteToUpdate = existingFavorite.firstOrNull { it.routeId == routeId }

            if (favoriteToUpdate != null) {
                val updatedFavorite = favoriteToUpdate.copy(
                    description = description ?: favoriteToUpdate.description,
                    routeId = newRouteId ?: favoriteToUpdate.routeId,
                )
                favoriteDao.update(updatedFavorite)
            }
        }
    }

    fun deleteFavoritesByUserId(userId: Int, routeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val favorites = favoriteDao.getByUserId(userId)
            val favoriteToDelete = favorites.firstOrNull { it.routeId == routeId }
            if (favoriteToDelete != null) {
                favoriteDao.delete(favoriteToDelete)
            }
        }
    }
}