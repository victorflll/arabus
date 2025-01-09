package com.example.arabus.repository.internal.entities

import androidx.room.*
import java.util.Date

@Entity(
    tableName = "history",
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
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "route_id") val routeId: Int,
    @ColumnInfo(name = "finished_at") val finishedAt: Date? = null
)
