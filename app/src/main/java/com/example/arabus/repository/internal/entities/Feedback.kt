package com.example.arabus.repository.internal.entities

import androidx.room.*

@Entity(
    tableName = "feedback",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class Feedback(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    val comment: String? = null,
    val rating: Int,
    @ColumnInfo(name = "plate_license") val plateLicense: String
)
