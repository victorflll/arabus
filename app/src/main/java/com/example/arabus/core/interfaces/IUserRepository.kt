package com.example.arabus.core.interfaces

import com.example.arabus.core.domain.user.User
import com.example.arabus.core.request.UserRequest
import java.util.UUID

interface IUserRepository {
    suspend fun getUser(): User?

    suspend fun createUser(user: UserRequest): UUID
}