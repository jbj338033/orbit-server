package com.orbit.domain.user.application.service

import com.orbit.domain.user.presentation.dto.response.UserMeResponse
import com.orbit.global.security.holder.SecurityHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val securityHolder: SecurityHolder
) {
    @Transactional(readOnly = true)
    fun getMe(): UserMeResponse {
        val user = securityHolder.user

        return UserMeResponse.of(user)
    }
}