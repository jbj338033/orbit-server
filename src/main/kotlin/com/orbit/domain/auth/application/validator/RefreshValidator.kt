package com.orbit.domain.auth.application.validator

import com.orbit.domain.auth.presentation.dto.request.RefreshRequest
import com.orbit.global.common.validator.Validator
import com.orbit.global.security.jwt.enums.JwtType
import com.orbit.global.security.jwt.provider.JwtProvider
import org.springframework.stereotype.Component

@Component
class RefreshValidator(
    private val jwtProvider: JwtProvider
): Validator<RefreshRequest> {
    override fun validate(target: RefreshRequest) {
        if (target.refreshToken.isBlank()) {
            throw IllegalArgumentException("Refresh token is required")
        }

        if (target.refreshToken.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"))) {
            throw IllegalArgumentException("Invalid refresh token format")
        }

        if (jwtProvider.getType(target.refreshToken) != JwtType.REFRESH) {
            throw IllegalArgumentException("Invalid refresh token")
        }
    }
}