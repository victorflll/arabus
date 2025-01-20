package com.example.arabus.repository.internal.entities

import androidx.room.*

@Entity(
    tableName = "role"
)
data class Role(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
