package com.example.arabus.repository.database

import AppDatabase
import android.content.Context
import androidx.room.Room

object DatabaseInstance {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if(INSTANCE == null) {
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "arabus_database"
                ).build()
                INSTANCE = instance
            }
        }
        return INSTANCE
    }
}
