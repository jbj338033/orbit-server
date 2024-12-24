package com.orbit.global.security.holder

import com.orbit.domain.user.domain.entity.UserEntity
import com.orbit.domain.user.domain.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SecurityHolder(
    private val userRepository: UserRepository
) {
    val user: UserEntity
        get() = userRepository.findByEmail(SecurityContextHolder.getContext().authentication.name)
            ?: throw IllegalStateException("User not found")
}