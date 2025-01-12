package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.Profile

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(profile: Profile)

    @Query("SELECT * FROM profile")
    suspend fun getAll(): List<Profile>


    @Query("SELECT * FROM profile WHERE user_id = :userId")
    suspend fun getById(userId: Int): Profile?

    @Update
    suspend fun update(profile: Profile)

    @Delete
    suspend fun delete(profile: Profile)
}
