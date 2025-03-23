package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.core.domain.favorite.FavoriteDomain
import com.example.arabus.core.request.FavoriteRequest
import com.example.arabus.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteRepository = FavoriteRepository()

    private val _favorites = MutableStateFlow<List<FavoriteDomain>>(emptyList())
    val favorites: StateFlow<List<FavoriteDomain>> = _favorites

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getFavoritesByUserId(favoriteRequest: FavoriteRequest, onResult: (List<FavoriteDomain>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val result = favoriteRepository.getFavoritesByUserId(favoriteRequest)
            _favorites.value = result
            onResult(result)
            _isLoading.value = false
        }
    }

    fun favoriteRoute(userId: UUID, routeId: UUID, onComplete: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = FavoriteRequest(userId = userId, routeId = routeId)
                favoriteRepository.saveFavorite(request)
                val updated = favoriteRepository.getFavoritesByUserId(request)
                _favorites.value = updated
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun unfavoriteRoute(userId: UUID, routeId: UUID, onComplete: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoriteRepository.deleteFavorite(userId, routeId)
                val updated = favoriteRepository.getFavoritesByUserId(FavoriteRequest(userId))
                _favorites.value = updated
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}
