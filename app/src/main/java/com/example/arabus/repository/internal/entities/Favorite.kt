package com.example.arabus.repository.internal.entities

import androidx.room.*
import java.util.Date

@Entity(
    tableName = "favorite",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Route::class,
            parentColumns = ["id"],
            childColumns = ["route_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "route_id") val routeId: Int,
    val description: String? = null,
    @ColumnInfo(name = "created_at") val createdAt: Date? = null
)
