package com.example.arabus.application.service.user

import com.example.arabus.ui.utils.Password
import com.example.arabus.ui.view.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthService(private val userViewModel: UserViewModel) {

    suspend fun validateCredentials(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val user = userViewModel.getUserByEmail(email) ?: return@withContext false

            Password.verifyPassword(password, user.password)
        }
    }

    suspend fun registerUser(email: String, password: String, roleId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                userViewModel.insertUser(email, password, roleId)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}