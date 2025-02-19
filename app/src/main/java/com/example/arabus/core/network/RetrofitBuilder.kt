package com.example.arabus.core.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "http://10.0.2.2:5001/"

    @Volatile
    private var authToken: String? = null

    fun setAuthToken(token: String) {
        authToken = token
    }

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()

        authToken?.let {
            request.addHeader("Authorization", it)
        }

        chain.proceed(request.build())
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    fun <T> createService(serviceClass: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(serviceClass)
    }
}