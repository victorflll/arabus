package com.example.arabus.core.network

import com.example.arabus.core.domain.user.User
import java.util.UUID

object UserManager {
    var id: UUID? = null
    var name: String? = null
    var email: String? = null
    var token: String? = null
    var profile: User? = null

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

    fun hasProfile(): Boolean {
        return profile != null
    }

    fun clearProfile() {
        profile = null
    }

    fun clear() {
        name = null
        token = null
        token = null
        profile = null
    }
}