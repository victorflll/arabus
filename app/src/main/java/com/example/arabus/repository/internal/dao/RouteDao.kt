package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.Route

@Dao
interface RouteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(route: Route)

    @Query("SELECT * FROM route WHERE id = :routeId")
    suspend fun getById(routeId: Int): Route?

    @Query("SELECT * FROM route WHERE available = 1")
    suspend fun getAvailableRoutes(): List<Route>

    @Delete
    suspend fun delete(route: Route)
}
