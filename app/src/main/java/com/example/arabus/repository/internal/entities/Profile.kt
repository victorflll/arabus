package com.example.arabus.repository.internal.entities

import androidx.room.*

@Entity(
    tableName = "profile",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Profile(
    @PrimaryKey @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    val name: String,
    val rating: Int? = null
)
