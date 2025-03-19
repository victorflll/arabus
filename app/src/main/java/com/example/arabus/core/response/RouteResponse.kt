package com.example.arabus.core.response

import com.example.arabus.core.domain.route.Location
import com.example.arabus.core.domain.route.Route
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.UUID


data class RouteResponse(
    val id: String,
    val code: String,
    val origin: Location,
    val destination: Location,
    @SerializedName("started_at")
    val startedAt: String,
    @SerializedName("finished_at")
    val finishedAt: String,
    val available: Boolean,
    val cost: Double,
    @SerializedName("picture_uri")
    val pictureUri: String,
    val rating: Double?
) {
    fun toEntity(): Route {

        val originLocation = Location(
            latitude = origin.latitude,
            longitude = origin.longitude,
            street = origin.street
        )
        val destinationLocation = Location(
            latitude = destination.latitude,
            longitude = destination.longitude,
            street = destination.street
        )

        return Route(
            id = UUID.fromString(this.id),
            available = this.available,
            cost = this.cost,
            origin = originLocation,
            destination = destinationLocation,
            startedAt = LocalDateTime.parse(this.startedAt),
            finishedAt = LocalDateTime.parse(this.finishedAt),
            code = this.code,
            pictureUri = this.pictureUri ?: "",
            rating = this.rating ?: 0.0
        )
    }
}