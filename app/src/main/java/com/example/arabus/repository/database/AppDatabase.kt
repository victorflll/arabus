package com.example.arabus.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.arabus.repository.internal.dao.*
import com.example.arabus.repository.internal.entities.*

@Database(
    entities = [
        User::class,
        Profile::class,
        Address::class,
        Role::class,
        Bus::class,
        Route::class,
        Favorite::class,
        History::class,
        Accessibility::class,
        Feedback::class,
        Notification::class
    ],
    version = 3,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun profileDao(): ProfileDao
    abstract fun addressDao(): AddressDao
    abstract fun roleDao(): RoleDao
    abstract fun busDao(): BusDao
    abstract fun routeDao(): RouteDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun historyDao(): HistoryDao
    abstract fun accessibilityDao(): AccessibilityDao
    abstract fun feedbackDao(): FeedbackDao
    abstract fun notificationDao(): NotificationDao
}
