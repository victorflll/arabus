package com.example.arabus.core.response

import com.example.arabus.core.domain.profile.Profile

class ProfileResponse(
    val name: String,
    val phone: String,
    val rating: Double?
) {
    fun toEntity(): Profile {
        return Profile(this.name, this.phone, this.rating)
    }
}