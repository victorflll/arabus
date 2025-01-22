package com.example.arabus.repository.database

import com.example.arabus.repository.internal.entities.Role
import com.example.arabus.repository.internal.entities.User
import com.example.arabus.repository.internal.entities.routesSeed
import com.example.arabus.ui.utils.Password
import java.util.Date

object DatabaseSeeder {
    suspend fun populateDatabase(db: AppDatabase) {
        val roleDao = db.roleDao()
        val userDao = db.userDao()
        val routeDao = db.routeDao()

        val roles = listOf(
            Role(name = "Admin"),
            Role(name = "User"),
            Role(name = "Driver"),
        )
        roles.forEach { role ->
            roleDao.insert(role)
        }

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

        val routes = routesSeed()
        routes.forEach { route ->
            routeDao.insert(route)
        }
    }
}