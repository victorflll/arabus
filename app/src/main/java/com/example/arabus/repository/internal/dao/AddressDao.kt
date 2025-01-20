package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.Address

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(address: Address)

    @Query("SELECT * FROM address WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): Address?

    @Update
    suspend fun update(address: Address)
}
