package com.example.arabus.repository.database

import android.util.Log
import com.example.arabus.repository.internal.entities.Notification
import com.example.arabus.repository.internal.entities.Role
import com.example.arabus.repository.internal.entities.User
import com.example.arabus.repository.internal.entities.historySeed
import com.example.arabus.repository.internal.entities.favoriteSeed
import com.example.arabus.repository.internal.entities.routesSeed
import com.example.arabus.ui.utils.Password
import java.util.Date

object DatabaseSeeder {
    suspend fun populateDatabase(db: AppDatabase) {
        val roleDao = db.roleDao()
        val userDao = db.userDao()
        val notificationDao = db.notificationDao()
        val routeDao = db.routeDao()
        val favoriteDao = db.favoriteDao()
        val historyDao = db.historyDao()

        if (roleDao.getAll().isEmpty()) {
            val roles = listOf(
                Role(name = "Admin"),
                Role(name = "User"),
                Role(name = "Driver"),
            )
            roles.forEach { role ->
                roleDao.insert(role)
            }
        }

        if (userDao.getAll().isEmpty()) {
            val users = listOf(
                User(
                    email = "admin@gmail.com",
                    password = Password.hashPassword("admin123"),
                    roleId = 1,
                    createdAt = Date()
                ),
                User(
                    email = "user@gmail.com",
                    password = Password.hashPassword("user123"),
                    roleId = 2,
                    createdAt = Date()
                )
            )
            users.forEach { user ->
                userDao.insert(user)
            }
        }

        val userId = 1

        val notifications = listOf(
            Notification(
                userId = userId,
                title = "Viagem atual",
                message = "victor gay!",
                timestamp = Date()
            ),
            Notification(
                userId = userId,
                title = "Viagem atual",
                message = "Seu ônibus está chegando...",
                timestamp = Date()
            ),
            Notification(
                userId = userId,
                title = "Centro - Deputado Nezinho",
                message = "Desembarque!",
                timestamp = Date()
            ),
            Notification(
                userId = userId,
                title = "Centro - Deputado Nezinho",
                message = "Seu ônibus chegou!",
                timestamp = Date()
            ),
            Notification(
                userId = userId,
                title = "Terminal - IFAL",
                message = "Ônibus atrasado",
                timestamp = Date()
            ),
            Notification(
                userId = userId,
                title = "Terminal - IFAL",
                message = "Ônibus saiu do ponto inicial",
                timestamp = Date()
            )
        )

        if (notificationDao.getAll().isEmpty()) {
            notifications.forEach { notification ->
                notificationDao.insert(notification)
            }
        }

        val routes = routesSeed()
        if (routeDao.getAvailableRoutes().isEmpty()) {
            routes.forEach { route ->
                routeDao.insert(route)
            }

        val historyList = historySeed(routeDao.getAvailableRoutes())
        if (historyList.isNotEmpty()) {
            historyList.forEach { historyItem ->
                historyDao.insert(historyItem)
            }
        }

        val favorites = favoriteSeed()
        if (favoriteDao.getAll().isEmpty()) {
            favorites.forEach { fav ->
                favoriteDao.insert(fav)
            }
        }
        }
    }
}