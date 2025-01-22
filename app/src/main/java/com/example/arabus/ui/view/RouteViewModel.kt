package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.core.dtos.RouteDto
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.util.Date

class RouteViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseInstance.getDatabase(application)
    private val routeDao = database.routeDao()

    fun insertRoute(
        routeCode: String,
        originLatitude: String,
        originLongitude: String,
        destinationLatitude: String,
        destinationLongitude: String,
        startedAt: Date,
        finishedAt: Date,
        cost: Double? = null,
        available: Boolean = true
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val route = Route(
                routeCode = routeCode,
                originLatitude = originLatitude,
                originLongitude = originLongitude,
                destinationLatitude = destinationLatitude,
                destinationLongitude = destinationLongitude,
                startedAt = startedAt,
                finishedAt = finishedAt,
                cost = cost,
                available = available
            )
            routeDao.insert(route)
        }
    }

    fun getRouteByCode(routeCode: String, onResult: (Route?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val route = routeDao.getByCode(routeCode)
            onResult(route)
        }
    }

    private val _routes = MutableStateFlow<List<RouteDto>>(emptyList())
    val routes: StateFlow<List<RouteDto>> = _routes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    suspend fun loadRoutes() {
        _isLoading.value = true
        val routes = withContext(Dispatchers.IO) {
            routeDao.getAvailableRoutes().map { route ->
                val startStreet = reverseGeocode(route.originLatitude, route.originLongitude)
                    ?: "Local desconhecido"
                val endStreet = reverseGeocode(route.destinationLatitude, route.destinationLongitude)
                    ?: "Local desconhecido"
                RouteDto(route, startStreet, endStreet)
            }
        }
        _routes.value = routes
        _isLoading.value = false
    }

    private suspend fun reverseGeocode(latitude: String, longitude: String): String? {
        val url =
            "https://nominatim.openstreetmap.org/reverse?lat=$latitude&lon=$longitude&format=json"
        return withContext(Dispatchers.IO) {
            try {
                val response = URL(url).readText()
                val jsonObject = JSONObject(response)
                jsonObject.getJSONObject("address").optString("road", "Local desconhecido")
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    fun updateRoute(
        routeCode: String,
        cost: Double?,
        available: Boolean?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingRoute = routeDao.getByCode(routeCode)
            if (existingRoute != null) {
                val updatedRoute = existingRoute.copy(
                    cost = cost ?: existingRoute.cost,
                    available = available ?: existingRoute.available
                )
                routeDao.update(updatedRoute)
            }
        }
    }

    fun deleteRoute(routeCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val routerToDelete = routeDao.getByCode(routeCode)
            if (routerToDelete != null) {
                routeDao.delete(routerToDelete)
            }
        }
    }
}