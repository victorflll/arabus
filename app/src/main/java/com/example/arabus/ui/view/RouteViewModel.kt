package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    suspend fun getRouteByCode(routeCode: String): Route? {
        return withContext(Dispatchers.IO) {
            routeDao.getByCode(routeCode)
        }
    }


    suspend fun getAllRoutes(): List<Route> {
        return withContext(Dispatchers.IO) {
            routeDao.getAvailableRoutes()
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
            if(routerToDelete != null ){
                routeDao.delete(routerToDelete)
            }
        }
    }
}
