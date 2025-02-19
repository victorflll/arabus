package com.example.arabus.core.interfaces

import com.example.arabus.core.domain.route.Route

interface IRouteRepository {
    suspend fun getRoutes(): List<Route>
}