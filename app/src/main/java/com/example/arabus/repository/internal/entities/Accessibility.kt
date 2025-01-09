package com.example.arabus.repository.internal.entities

import androidx.room.*

@Entity(
    tableName = "accessibility",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Accessibility(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    val colorblind: Int? = null,
    @ColumnInfo(name = "one_handed") val oneHanded: Boolean = false,
    val contrast: Boolean = false,
    @ColumnInfo(name = "screen_reader") val screenReader: Boolean = false
)
