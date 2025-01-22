package com.example.arabus.repository.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.arabus.CoroutineScopeProvider
import kotlinx.coroutines.launch

object DatabaseInstance {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "arabus_database"
            ).addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val applicationScope =
                        (context.applicationContext as CoroutineScopeProvider).ioScope
                    applicationScope.launch {
                        INSTANCE?.let { appDatabase ->
                            DatabaseSeeder.populateDatabase(appDatabase)
                        }
                    }
                }
            }).build()
            INSTANCE = instance
            instance
        }
    }
}