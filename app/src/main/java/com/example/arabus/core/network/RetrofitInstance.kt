package com.example.arabus.core.network

import com.example.arabus.core.interfaces.IAuthApi
import com.example.arabus.core.interfaces.IHistoryAPI
import com.example.arabus.core.interfaces.IUserApi
import com.example.arabus.core.interfaces.IFavoriteAPI
import com.example.arabus.core.interfaces.INotificationAPI
import com.example.arabus.core.interfaces.IRouteAPI

object RetrofitInstance {
    val user: IUserApi by lazy {
        RetrofitBuilder.createService(IUserApi::class.java)
    }

    val auth: IAuthApi by lazy {
        RetrofitBuilder.createService(IAuthApi::class.java)
    }

    val route: IRouteAPI by lazy {
        RetrofitBuilder.createService(IRouteAPI::class.java)
    }

    val history: IHistoryAPI by lazy {
        RetrofitBuilder.createService(IHistoryAPI::class.java)
    }

    val notification: INotificationAPI by lazy {
        RetrofitBuilder.createService(INotificationAPI::class.java)
    }

    val favorite: IFavoriteAPI by lazy {
        RetrofitBuilder.createService(IFavoriteAPI::class.java)
    }
}