package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.Role

@Dao
interface RoleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(role: Role)

    @Query("SELECT * FROM role WHERE id = :roleId")
    suspend fun getById(roleId: Int): Role?

    @Query("SELECT * FROM role")
    suspend fun getAll(): List<Role>

    @Delete
    suspend fun delete(role: Role)
}
