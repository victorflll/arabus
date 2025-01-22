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

    calendar.add(Calendar.MINUTE, Random.nextInt(1, 31))
    val finishedAt = calendar.time

    return listOf(
        Route(
            routeCode = Random.nextInt(1, 5).toString(),
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
            routeCode = Random.nextInt(1, 5).toString(),
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
            routeCode = Random.nextInt(1, 5).toString(),
            originLatitude = "-9.7600",
            originLongitude = "-36.6700",
            destinationLatitude = "-9.7650",
            destinationLongitude = "-36.6800",
            startedAt = startedAt,
            finishedAt = finishedAt,
            pictureUri = "real-logo",
            cost = Random.nextDouble(0.0, 5.0),
            available = true
        ),
        Route(
            routeCode = Random.nextInt(1, 5).toString(),
            originLatitude = "-9.7511",
            originLongitude = "-36.6608",
            destinationLatitude = "-9.7470",
            destinationLongitude = "-36.6575",
            startedAt = startedAt,
            finishedAt = finishedAt,
            pictureUri = "metropolitana-logo",
            cost = Random.nextDouble(0.0, 5.0),
            available = true
        ),
        Route(
            routeCode = Random.nextInt(1, 5).toString(),
            originLatitude = "-9.7570",
            originLongitude = "-36.6725",
            destinationLatitude = "-9.7568",
            destinationLongitude = "-36.6660",
            startedAt = startedAt,
            finishedAt = finishedAt,
            pictureUri = "real-logo",
            cost = Random.nextDouble(0.0, 5.0),
            available = true
        ),
        Route(
            routeCode = Random.nextInt(1, 5).toString(),
            originLatitude = "-9.7575",
            originLongitude = "-36.6590",
            destinationLatitude = "-9.7620",
            destinationLongitude = "-36.6635",
            startedAt = startedAt,
            finishedAt = finishedAt,
            pictureUri = "metropolitana-logo",
            cost = Random.nextDouble(0.0, 5.0),
            available = true
        ),
        Route(
            routeCode = Random.nextInt(1, 5).toString(),
            originLatitude = "-9.7540",
            originLongitude = "-36.6650",
            destinationLatitude = "-9.7550",
            destinationLongitude = "-36.6620",
            startedAt = startedAt,
            finishedAt = finishedAt,
            pictureUri = "real-logo",
            cost = Random.nextDouble(0.0, 5.0),
            available = true
        ),
        Route(
            routeCode = Random.nextInt(1, 5).toString(),
            originLatitude = "-9.7560",
            originLongitude = "-36.6610",
            destinationLatitude = "-9.7570",
            destinationLongitude = "-36.6595",
            startedAt = startedAt,
            finishedAt = finishedAt,
            pictureUri = "metropolitana-logo",
            cost = Random.nextDouble(0.0, 5.0),
            available = true
        ),
        Route(
            routeCode = Random.nextInt(1, 5).toString(),
            originLatitude = "-9.7535",
            originLongitude = "-36.6680",
            destinationLatitude = "-9.7545",
            destinationLongitude = "-36.6700",
            startedAt = startedAt,
            finishedAt = finishedAt,
            pictureUri = "real-logo",
            cost = Random.nextDouble(0.0, 5.0),
            available = true
        ),
        Route(
            routeCode = Random.nextInt(1, 5).toString(),
            originLatitude = "-9.7552",
            originLongitude = "-36.6638",
            destinationLatitude = "-9.7525",
            destinationLongitude = "-36.6610",
            startedAt = startedAt,
            finishedAt = finishedAt,
            pictureUri = "metropolitana-logo",
            cost = Random.nextDouble(0.0, 5.0),
            available = true
        )
    )

}

