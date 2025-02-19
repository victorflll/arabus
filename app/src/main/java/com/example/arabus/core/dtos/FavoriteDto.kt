package com.example.arabus.core.dtos

import com.example.arabus.repository.internal.entities.Favorite

data class FavoriteDto(
    val route: RouteDto,
    val favorite: Favorite
)