package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.Bus
import com.example.arabus.repository.internal.entities.Favorite

@Dao
interface BusDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(bus: Bus)

    @Query("SELECT * FROM bus WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): List<Bus>

    @Query("SELECT * FROM bus WHERE license_plate = :licensePlate")
    suspend fun getById(licensePlate: String): Bus?

    @Query("SELECT * FROM bus")
    suspend fun getAll(): List<Bus>

    @Update
    suspend fun update(bus: Bus)
}
