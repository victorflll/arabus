package com.example.arabus.repository.internal.entities

import androidx.room.*

@Entity(
    tableName = "bus",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["license_plate"], unique = true)]
)
data class Bus(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "license_plate") val licensePlate: String,
    val color: String? = null,
    @ColumnInfo(name = "user_id") val userId: Int? = null,
    val company: String? = null,
    @ColumnInfo(name = "image_url") val imageUrl: String? = null
)
