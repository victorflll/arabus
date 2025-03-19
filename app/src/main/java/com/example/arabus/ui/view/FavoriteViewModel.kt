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

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteRepository = FavoriteRepository()

    private val _favorites = MutableStateFlow<List<FavoriteDomain>>(emptyList())
    val favorites: StateFlow<List<FavoriteDomain>> = _favorites

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getFavoritesByUserId(favoriteRequest: FavoriteRequest, onResult: (List<FavoriteDomain>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val apiFavorites = favoriteRepository.getFavoritesByUserId(favoriteRequest)
            _favorites.value = apiFavorites
            onResult(apiFavorites)
            _isLoading.value = false
        }
    }
}
