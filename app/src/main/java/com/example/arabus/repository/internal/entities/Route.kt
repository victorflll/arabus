package com.example.arabus.repository.internal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date
import kotlin.random.Random


@Entity(
    tableName = "route"
)
data class Route(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "route_code") val routeCode: String,
    @ColumnInfo(name = "origin_latitude") val originLatitude: String,
    @ColumnInfo(name = "origin_longitude") val originLongitude: String,
    @ColumnInfo(name = "destination_latitude") val destinationLatitude: String,
    @ColumnInfo(name = "destination_longitude") val destinationLongitude: String,
    @ColumnInfo(name = "started_at") val startedAt: Date,
    @ColumnInfo(name = "finished_at") val finishedAt: Date,
    @ColumnInfo(name = "picture_uri") val pictureUri: String? = null,
    val cost: Double? = null,
    val available: Boolean = true
)

fun routesSeed(): List<Route> {
    val calendar = Calendar.getInstance()

    val startedAt = calendar.time

    val routeCode = Random.nextInt(100, 1000).toString()

    val randomMinutes = Random.nextInt(1, 31)
    calendar.add(Calendar.MINUTE, randomMinutes)
    val finishedAt = calendar.time

    return listOf(
        Route(
            routeCode = routeCode,
            originLatitude = "-9.7557",
            originLongitude = "-36.6615",
            destinationLatitude = "-9.7563",
            destinationLongitude = "-36.6700",
            startedAt = startedAt,
            finishedAt = finishedAt,
            pictureUri = "real-logo",
            cost = Random.nextDouble(0.0, 5.0),
            available = true
        ),
        Route(
            routeCode = routeCode,
            originLatitude = "-9.7500",
            originLongitude = "-36.6600",
            destinationLatitude = "-9.7450",
            destinationLongitude = "-36.6550",
            startedAt = startedAt,
            finishedAt = finishedAt,
            pictureUri = "metropolitana-logo",
            cost = Random.nextDouble(0.0, 5.0),
            available = true
        ),
        Route(
            routeCode = routeCode,
            originLatitude = "-9.7600",
            originLongitude = "-36.6700",
            destinationLatitude = "-9.7650",
            destinationLongitude = "-36.6800",
            startedAt = startedAt,
            finishedAt = finishedAt,
            pictureUri = "real-logo",
            cost = Random.nextDouble(0.0, 5.0),
            available = true
        )
    )
}

