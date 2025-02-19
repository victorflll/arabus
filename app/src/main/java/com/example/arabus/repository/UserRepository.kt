package com.example.arabus.repository

import com.example.arabus.core.domain.user.User
import com.example.arabus.core.interfaces.IUserRepository
import com.example.arabus.core.network.RetrofitInstance
import com.example.arabus.core.request.UserRequest
import java.util.UUID

class UserRepository : IUserRepository {
    private val api = RetrofitInstance.user

    override suspend fun getUser(): User? {
        try {
            val response = api.getUser()

            if (response.isSuccessful) {
                val user = response.body()?.toEntity()
                return user
            } else {
                val errorCode = response.code()
                val errorBody = response.errorBody()?.string()

                throw Exception("Erro de API: Código $errorCode - $errorBody")
            }
        } catch (e: Exception) {
            throw Exception("Erro ao buscar usuário: ${e.message}")
        }
    }

    override suspend fun createUser(user: UserRequest): UUID {
        try {
            val response = api.createUser(user)

            if (response.isSuccessful) {
                val id = response.body()!!.id
                return id
            } else {
                val errorCode = response.code()
                val errorBody = response.errorBody()?.string()

                throw Exception("Erro de API: Código $errorCode - $errorBody")
            }
        } catch (e: Exception) {
            throw Exception("Erro ao criar usuário: ${e.message}")
        }
    }
}

