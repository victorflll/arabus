package com.example.arabus.core.interfaces

import com.example.arabus.core.response.RouteResponse
import retrofit2.Response
import retrofit2.http.GET

interface IRouteAPI {
    @GET("route")
    suspend fun getRoutes(): Response<List<RouteResponse>>
}