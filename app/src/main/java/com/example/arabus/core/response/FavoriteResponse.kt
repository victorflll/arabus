package com.example.arabus.core.response

import com.example.arabus.core.domain.favorite.FavoriteDomain
import com.example.arabus.core.response.RouteResponse
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.UUID

data class FavoriteResponse(
    val id: UUID,
    @SerializedName("user_id")
    val userId: UUID,
    @SerializedName("route_id")
    val routeId: UUID,
    val route: RouteResponse,
    val description: String?,
    @SerializedName("created_at")
    val createdAt: String
) {
    fun toEntity(): FavoriteDomain {
        return FavoriteDomain(
            id = this.id,
            userId = this.userId,
            route = this.route.toEntity(),
            description = this.description,
            createdAt = LocalDateTime.parse(this.createdAt)
        )
    }
}
