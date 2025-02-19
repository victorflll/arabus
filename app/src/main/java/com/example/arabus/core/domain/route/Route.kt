package com.example.arabus.core.domain.route

import java.time.LocalDateTime
import java.util.UUID

data class Route(
    val id: UUID,
    val available: Boolean,
    val cost: Double,
    val origin: Location,
    val destination: Location,
    val startedAt: LocalDateTime,
    val finishedAt: LocalDateTime,
    val code: String,
    val pictureUri: String,
    val rating: Double
)

data class Location(
    val latitude: String,
    val longitude: String,
    val street: String
)