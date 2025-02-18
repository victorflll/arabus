package com.example.arabus.core.interfaces

import com.example.arabus.core.request.UserRequest
import com.example.arabus.repository.internal.entities.User
import java.util.UUID

interface IUserRepository {
    suspend fun getUser(): User?

    suspend fun createUser(user: UserRequest): UUID
}