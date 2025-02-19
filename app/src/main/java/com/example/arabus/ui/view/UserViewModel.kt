package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.core.domain.user.User
import com.example.arabus.core.request.LoginRequest
import com.example.arabus.core.request.UserRequest
import com.example.arabus.ui.DIContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = DIContainer.getUserRepository()
    private val authRepository = DIContainer.getAuthRepository()

    fun createUser(user: UserRequest, onResult: (UUID) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userRepository.createUser(user)

            withContext(Dispatchers.Main) {
                onResult(userId)
            }
        }
    }

    fun login(login: LoginRequest,onResult: (String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = authRepository.login(login)
            onResult(token)
        }
    }

    fun getUser(onResult: (User?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.getUser()
            onResult(user)
        }
    }

//    private val userDao = DatabaseInstance.getDatabase(application).userDao()
//
//    fun insertUser(email: String, password: String, roleId: Int, onResult: (Long) -> Unit) {
//        viewModelScope.launch(Dispatchers.IO) {
//
//
//            val now = Date()
//            val user = User(
//                email = email,
//                password = Password.hashPassword(password),
//                roleId = roleId,
//                createdAt = now,
//                updatedAt = now
//            )
//
//            val userId = userDao.insert(user)
//
//            withContext(Dispatchers.Main) {
//                onResult(userId)
//            }
//        }
//    }
//
//    fun getAllUsers(onResult: (List<User>) -> Unit) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val users = userDao.getAll()
//            onResult(users)
//        }
//    }
//
//    fun getUserById(userId: Int, onResult: (User?) -> Unit) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val user = userDao.getById(userId)
//            onResult(user)
//        }
//    }
//
//    suspend fun getUserByEmail(email: String): User? {
//        return withContext(Dispatchers.IO) {
//            userDao.getByEmail(email)
//        }
//    }
//
//    fun updateUser(userId: Int, email: String, password: String?) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val existingUser = userDao.getById(userId)
//
//
//            if (existingUser != null) {
//                if (existingUser.email != email) {
//                    throw IllegalArgumentException("E-mail inválido!")
//                }
//
//                val now = Date()
//                val newPassword = password ?: existingUser.password
//
//                val updatedUser = existingUser.copy(
//                    password = Password.hashPassword(newPassword),
//                    updatedAt = now
//                )
//                userDao.update(updatedUser)
//            }
//        }
//    }
//
//    fun deleteUser(userId: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val user = userDao.getById(userId)
//            if (user != null) {
//                userDao.delete(user)
//            }
//        }
//    }
}
