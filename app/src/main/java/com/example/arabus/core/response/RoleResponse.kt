package com.example.arabus.core.response

import com.example.arabus.core.domain.role.Role
import com.example.arabus.core.domain.user.User
import java.util.UUID

class RoleResponse(
    val id: UUID,
    val name: String
) {
    fun toEntity(): Role {
        return Role(this.id, this.name)
    }
}