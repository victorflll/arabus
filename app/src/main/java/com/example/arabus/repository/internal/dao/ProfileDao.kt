package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.Profile

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: Profile)

    @Query("SELECT * FROM profile WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): Profile?

    @Delete
    suspend fun delete(profile: Profile)
}
