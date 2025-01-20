package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.Accessibility

@Dao
interface AccessibilityDao {
    @Query("SELECT * FROM accessibility WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): Accessibility?

    @Update
    suspend fun update(accessibility: Accessibility)
}
