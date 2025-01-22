package com.example.arabus.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.internal.dao.FavoriteDao
import com.example.arabus.repository.internal.models.FavoriteWithRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteDao: FavoriteDao) : ViewModel() {

    fun getFavoritesWithRoutes(userId: Int, onResult: (List<FavoriteWithRoute>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val favorites = favoriteDao.getFavoritesWithRoutes(userId)
                onResult(favorites)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(emptyList())
            }
        }
    }
}
