package com.example.arabus.ui.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.arabus.ui.view.FavoriteViewModel
import com.example.arabus.ui.view.RouteViewModel

class FavoriteViewModelFactory(
    private val application: Application,
    private val routeViewModel: RouteViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(application, routeViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}