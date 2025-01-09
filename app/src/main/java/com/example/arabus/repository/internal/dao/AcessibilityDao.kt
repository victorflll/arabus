package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.Accessibility

@Dao
interface AccessibilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(accessibility: Accessibility)

    @Query("SELECT * FROM accessibility WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): Accessibility?

    @Delete
    suspend fun delete(accessibility: Accessibility)
}
