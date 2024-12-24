package com.orbit.domain.user.presentation.dto.response

import com.orbit.domain.user.domain.entity.UserEntity
import java.util.UUID

data class UserMeResponse(
    val id: UUID,
    val name: String,
    val email: String
) {
    companion object {
        fun of(user: UserEntity) = UserMeResponse(
            id = user.id!!,
            name = user.name,
            email = user.email
        )
    }
}