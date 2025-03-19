package com.example.arabus.core.network

import java.util.UUID

object UserManager {
    var id: UUID? = null
    var name: String? = null
    var email: String? = null
    var token: String? = null

    fun hasId(): Boolean {
        return id != null
    }

    fun clearId() {
        id = null
    }

    fun hasName(): Boolean {
        return !name.isNullOrEmpty()
    }

    fun clearName() {
        name = null
    }

    fun hasEmail(): Boolean {
        return !email.isNullOrEmpty()
    }

    fun clearEmail() {
        email = null
    }

    fun hasToken(): Boolean {
        return !token.isNullOrEmpty()
    }

    fun clearToken() {
        token = null
    }

    fun clear() {
        name = null
        token = null
        token = null
    }
}