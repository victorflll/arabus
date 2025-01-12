
package com.example.arabus.repository.internal.entities

import androidx.room.*
import java.util.Date

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
    val cost: Double? = null,
    val available: Boolean = true
)