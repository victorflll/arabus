package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.User
import com.example.arabus.utils.PasswordUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = DatabaseInstance.getDatabase(application).userDao()

    fun insertUser(email: String, password: String, roleId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val now = Date()
            val user = User(
                email = email,
                password = PasswordUtil.hashPassword(password),
                roleId = roleId,
                createdAt = now,
                updatedAt = now
            )
            userDao.insert(user)
        }
    }

    suspend fun getAllUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            userDao.getAll()
        }
    }

    suspend fun getUserById(userId: Int): User? {
        return withContext(Dispatchers.IO) {
            userDao.getById(userId)
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.getByEmail(email)
        }
    }

    fun updateUser(userId: Int, email: String, password: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingUser = userDao.getById(userId)


            if (existingUser != null) {
                if (existingUser.email != email) {
                    throw IllegalArgumentException("E-mail inválido!")
                }

                val now = Date()
                val newPassword = password ?: existingUser.password

                val updatedUser = existingUser.copy(
                    password = PasswordUtil.hashPassword(newPassword),
                    updatedAt = now
                )
                userDao.update(updatedUser)
            }
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userDao.getById(userId)
            if (user != null) {
                userDao.delete(user)
            }
        }
    }
}
