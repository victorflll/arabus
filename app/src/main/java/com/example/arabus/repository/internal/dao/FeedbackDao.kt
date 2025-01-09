package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.Feedback

@Dao
interface FeedbackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(feedback: Feedback)

    @Query("SELECT * FROM feedback WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): List<Feedback>

    @Query("SELECT * FROM feedback WHERE driver_id = :driverId")
    suspend fun getByDriverId(driverId: Int): List<Feedback>

    @Delete
    suspend fun delete(feedback: Feedback)
}
