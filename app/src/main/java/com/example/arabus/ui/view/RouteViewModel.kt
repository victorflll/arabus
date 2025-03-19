package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.arabus.core.domain.route.Route
import com.example.arabus.repository.RouteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class RouteViewModel(application: Application) : AndroidViewModel(application) {
    private val routeRepository = RouteRepository()

    private val _routes = MutableStateFlow<List<Route>>(emptyList())
    val routes: StateFlow<List<Route>> = _routes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    suspend fun loadRoutes() {
        _isLoading.value = true
        val routes = withContext(Dispatchers.IO) {
            routeRepository.getRoutes().map { route ->
                val startStreet = route.origin.street
                val endStreet = route.destination.street
                route.copy(
                    origin = route.origin.copy(street = startStreet),
                    destination = route.destination.copy(street = endStreet)
                )
            }
        }
        _routes.value = routes
        _isLoading.value = false
    }
}
