package com.example.arabus.core.dtos

import com.example.arabus.repository.internal.entities.Route

data class RouteDto(
    val route: Route,
    val startStreet: String,
    val endStreet: String
)
