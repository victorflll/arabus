package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.Route

@Dao
interface RouteDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(route: Route)

    @Query("SELECT * FROM route WHERE route_code = :routerCode")
    suspend fun getByCode(routerCode: String): Route?

    @Query("SELECT * FROM route WHERE available = 1")
    suspend fun getAvailableRoutes(): List<Route>

    @Update
    suspend fun update(route: Route)

    @Delete
    suspend fun delete(route: Route)
}
