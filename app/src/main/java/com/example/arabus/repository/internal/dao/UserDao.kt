package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.User


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getById(userId: Int): User?


    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getByEmail(email: String): User?


    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}
