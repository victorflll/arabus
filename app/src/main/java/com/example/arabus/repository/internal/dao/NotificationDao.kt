package com.example.arabus.repository.internal.dao

import androidx.room.*
import com.example.arabus.repository.internal.entities.Notification
import com.example.arabus.repository.internal.entities.Role

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(notification: Notification)

    @Query("SELECT * FROM notifications")
    suspend fun getAll(): List<Notification>

    @Query("SELECT * FROM notifications WHERE user_id = :userId ORDER BY timestamp DESC")
    suspend fun getByUserId(userId: Int): List<Notification>

    @Query("SELECT * FROM notifications WHERE id = :notificationId LIMIT 1")
    suspend fun getById(notificationId: Int): Notification?

    @Update
    suspend fun update(notification: Notification)

    @Delete
    suspend fun delete(notification: Notification)


}
