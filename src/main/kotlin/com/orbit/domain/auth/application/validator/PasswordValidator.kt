package com.orbit.domain.auth.application.validator

import com.orbit.global.common.validator.Validator
import org.springframework.stereotype.Component

@Component
class PasswordValidator: Validator<String> {
    override fun validate(target: String) {
        if (target.length < 8) {
            throw IllegalArgumentException("Password must be at least 8 characters long")
        }

        if (!target.matches(Regex(".*[0-9].*"))) {
            throw IllegalArgumentException("Password must contain at least one number")
        }

        if (!target.matches(Regex(".*[a-z].*"))) {
            throw IllegalArgumentException("Password must contain at least one lowercase letter")
        }

        if (!target.matches(Regex(".*[!@#\$%^&*].*"))) {
            throw IllegalArgumentException("Password must contain at least one special character")
        }

        if (target.contains(" ")) {
            throw IllegalArgumentException("Password must not contain any whitespace")
        }
    }
}