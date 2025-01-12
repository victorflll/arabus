package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.Feedback
import com.example.arabus.repository.internal.entities.History

@Dao
interface FeedbackDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(feedback: Feedback)

    @Query("SELECT * FROM feedback WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): List<Feedback>

    @Query("SELECT * FROM feedback WHERE plate_license = :plateLicense")
    suspend fun getByDriverId(plateLicense: String): List<Feedback>

    @Update
    suspend fun update(feedback: Feedback)

}
