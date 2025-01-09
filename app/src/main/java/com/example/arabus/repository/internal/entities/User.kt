package com.example.arabus.repository.internal.entities

import androidx.room.*
import java.util.Date

@Entity(
    tableName = "user",
    foreignKeys = [
        ForeignKey(
            entity = Role::class,
            parentColumns = ["id"],
            childColumns = ["role_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["email"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val password: String,
    @ColumnInfo(name = "role_id") val roleId: Int? = null,
    @ColumnInfo(name = "created_at") val createdAt: Date? = null,
    @ColumnInfo(name = "updated_at") val updatedAt: Date? = null
)
