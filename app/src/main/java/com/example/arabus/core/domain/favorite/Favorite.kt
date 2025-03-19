package com.example.arabus.core.domain.favorite
import com.example.arabus.core.domain.route.Route
import java.time.LocalDateTime
import java.util.UUID

data class FavoriteDomain(
    val id: UUID,
    val userId: UUID,
    val route: Route,
    val description: String?,
    val createdAt: LocalDateTime
)
