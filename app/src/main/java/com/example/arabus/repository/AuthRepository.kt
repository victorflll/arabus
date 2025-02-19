package com.example.arabus.repository

import com.example.arabus.core.interfaces.IAuthRepository
import com.example.arabus.core.network.RetrofitBuilder
import com.example.arabus.core.network.RetrofitInstance
import com.example.arabus.core.network.UserManager
import com.example.arabus.core.request.LoginRequest

class AuthRepository : IAuthRepository {
    private val api = RetrofitInstance.auth

    override suspend fun login(loginRequest: LoginRequest): String {
        try {
            val response = api.login(loginRequest)
            if (response.isSuccessful) {
                val token = response.body()!!.token
                UserManager.token = token
                RetrofitBuilder.setAuthToken(token)
                return token
            } else {
                val errorCode = response.code()
                val errorBody = response.errorBody()?.string()

                throw Exception("Erro de API: Código $errorCode - $errorBody")
            }
        } catch (e: Exception) {
            throw Exception("Erro ao fazer login: ${e.message}")
        }
    }
}