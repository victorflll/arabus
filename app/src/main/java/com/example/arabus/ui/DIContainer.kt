package com.example.arabus.ui

import com.example.arabus.core.interfaces.IAuthRepository
import com.example.arabus.core.interfaces.IUserRepository
import com.example.arabus.repository.AuthRepository
import com.example.arabus.repository.UserRepository

class DIContainer {
    companion object {
        fun getAuthRepository() : IAuthRepository {
            return AuthRepository()
        }

        fun getUserRepository() : IUserRepository {
            return UserRepository()
        }
    }
}