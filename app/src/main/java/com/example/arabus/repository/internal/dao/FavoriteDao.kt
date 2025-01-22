package com.example.arabus.repository.internal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.arabus.repository.internal.entities.Favorite

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(favorite: Favorite)

    @Query("SELECT * FROM favorite WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): List<Favorite>

    @Query("SELECT * FROM favorite")
    suspend fun getAll(): List<Favorite>
}