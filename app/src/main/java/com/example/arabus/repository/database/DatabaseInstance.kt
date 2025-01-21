package com.example.arabus.repository.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object DatabaseInstance {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    @OptIn(DelicateCoroutinesApi::class)
    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "arabus_database"
            ).addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    GlobalScope.launch {
                        INSTANCE?.let { appDatabase ->
                            DatabaseSeeder.populateDatabase(appDatabase)
                        }
                    }
                }
            }).build()
            INSTANCE = instance
            return instance
        }
    }
}