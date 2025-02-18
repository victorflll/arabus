package com.example.arabus.core.network

import com.example.arabus.core.interfaces.IAuthApi
import com.example.arabus.core.interfaces.IUserApi

object RetrofitInstance {
    val user: IUserApi by lazy {
        RetrofitBuilder.createService(IUserApi::class.java)
    }

    val auth: IAuthApi by lazy {
        RetrofitBuilder.createService(IAuthApi::class.java)
    }
}