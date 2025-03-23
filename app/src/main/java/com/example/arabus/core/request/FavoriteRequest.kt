package com.example.arabus.core.request

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FavoriteRequest(
    @SerializedName("user_id")
    val userId: UUID,

    @SerializedName("route_id")
    val routeId: UUID? = null,

    val description: String = "Minha rota favorita"
)


