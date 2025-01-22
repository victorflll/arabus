package com.example.arabus.repository.internal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.arabus.repository.internal.models.FavoriteWithRoute
import com.example.arabus.repository.internal.entities.Favorite

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(favorite: Favorite)

    @Query("SELECT * FROM favorite WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): List<Favorite>

    @Transaction
    @Query(
        """
    SELECT f.*, 
           r.route_code, 
           r.origin_latitude, 
           r.origin_longitude, 
           r.destination_latitude, 
           r.destination_longitude, 
           r.picture_uri, 
           r.cost,
           r.started_at, 
           r.finished_at 
    FROM favorite f
    INNER JOIN route r ON f.route_id = r.id
    WHERE f.user_id = :userId
"""
    )
    suspend fun getFavoritesWithRoutes(userId: Int): List<FavoriteWithRoute>
}
