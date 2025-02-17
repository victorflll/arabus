package com.example.arabus.core.network

import com.example.arabus.application.interfaces.user.UserApiInterface

object RetrofitInstance {

    val userApi: UserApiInterface by lazy {
        RetrofitBuilder.createService(UserApiInterface::class.java)
    }
}