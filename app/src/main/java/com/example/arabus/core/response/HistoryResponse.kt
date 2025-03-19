package com.example.arabus.core.response

import com.example.arabus.core.domain.history.History
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.UUID

data class HistoryResponse(
    @SerializedName("finished_at")
    val finishedAt: String,
    val id: UUID,
    val route: RouteResponse,
    @SerializedName("route_id")
    val routeId: String,
    @SerializedName("user_id")
    val userId: UUID
) {
    fun toEntity(): History {
        return History(
            userId = this.userId,
            id = this.id,
            route = this.route.toEntity(),
            finishedAt = LocalDateTime.parse(this.finishedAt)
        )
    }
}
