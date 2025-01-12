package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.History

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(history: History)

    @Query("SELECT * FROM history WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): List<History>

    @Query("SELECT * FROM history WHERE route_id = :routeId")
    suspend fun getByRouteId(routeId: Int): List<History>

}
