package com.example.arabus.repository.internal.models

import androidx.room.ColumnInfo
import java.util.Date

data class FavoriteWithRoute(
    val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "route_id") val routeId: Int,
    val description: String?,
    @ColumnInfo(name = "route_code") val routeCode: String,
    @ColumnInfo(name = "origin_latitude") val originLatitude: String,
    @ColumnInfo(name = "origin_longitude") val originLongitude: String,
    @ColumnInfo(name = "destination_latitude") val destinationLatitude: String,
    @ColumnInfo(name = "destination_longitude") val destinationLongitude: String,
    @ColumnInfo(name = "started_at") val startedAt: Date,
    @ColumnInfo(name = "finished_at") val finishedAt: Date,
    @ColumnInfo(name = "picture_uri") val pictureUri: String?,
    @ColumnInfo(name = "cost") val cost: Double?
)

