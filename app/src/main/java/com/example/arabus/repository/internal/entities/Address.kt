package com.example.arabus.repository.internal.entities

import androidx.room.*

@Entity(
    tableName = "address",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Address(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    val neighborhood: String? = null,
    val street: String? = null,
    val postcode: String? = null
)
