package com.example.arabus.application.service.user

import com.example.arabus.ui.view.ProfileViewModel
import com.example.arabus.ui.view.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterService(private val userViewModel: UserViewModel, private val profileViewModel: ProfileViewModel) {

    suspend fun registerUser(name: String, phone: String, email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val existingUser = userViewModel.getUserByEmail(email)
                if (existingUser?.email != null) {
                    throw IllegalStateException("Usuário já existe")
                }

                userViewModel.insertUser(email, password, 2) { userId ->
                    profileViewModel.insertProfile(name, phone, 0, userId.toInt())
                }
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}