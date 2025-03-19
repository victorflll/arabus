package com.example.arabus.core.dtos

import com.example.arabus.core.domain.favorite.FavoriteDomain

data class FavoriteDto(
    val route: RouteDto,
    val favorite: FavoriteDomain
)