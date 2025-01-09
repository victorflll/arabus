package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.User


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)// retornar erro
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getById(userId: Int): User?

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Delete
    suspend fun delete(user: User)
}
