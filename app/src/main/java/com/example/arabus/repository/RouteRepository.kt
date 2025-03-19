package com.example.arabus.repository

import com.example.arabus.core.domain.route.Route
import com.example.arabus.core.interfaces.IRouteRepository
import com.example.arabus.core.network.RetrofitInstance

class RouteRepository : IRouteRepository {
    private val api = RetrofitInstance.route

    override suspend fun getRoutes(): List<Route> {
        try {
            val response = api.getRoutes()

            if (response.isSuccessful) {
                return response.body()?.map { it.toEntity() } ?: emptyList()
            } else {
                val errorCode = response.code()
                val errorBody = response.errorBody()?.string()
                throw Exception("Erro de API: Código $errorCode - $errorBody")
            }
        } catch (e: Exception) {
            throw Exception("Erro ao buscar rotas: ${e.message}")
        }
    }
}