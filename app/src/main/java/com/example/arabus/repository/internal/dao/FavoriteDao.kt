package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.Favorite

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Query("SELECT * FROM favorite WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): List<Favorite>

    @Delete
    suspend fun delete(favorite: Favorite)
}
