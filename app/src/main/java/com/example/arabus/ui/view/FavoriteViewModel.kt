package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.core.dtos.FavoriteDto
import com.example.arabus.repository.database.DatabaseInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application, private val routeViewModel: RouteViewModel) :
    AndroidViewModel(application) {
    private val database = DatabaseInstance.getDatabase(application)
    private val favoriteDao = database.favoriteDao()

    private val _favorites = MutableStateFlow<List<FavoriteDto>>(emptyList())
    val favorites: StateFlow<List<FavoriteDto>> = _favorites

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val favorites = favoriteDao.getAll()

            val routes = routeViewModel.loadRoutes()

            val dtos = favorites.mapNotNull { favorite ->
                val route = routeViewModel.routes.value.find { it.route.id == favorite.routeId }
                if (route != null) {
                    FavoriteDto(route, favorite)
                } else {
                    null
                }
            }
            _favorites.value = dtos
            _isLoading.value = false
        }
    }
}