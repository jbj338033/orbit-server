package com.orbit.domain.auth.application.validator

import com.orbit.global.common.validator.Validator
import org.springframework.stereotype.Component

@Component
class EmailValidator: Validator<String> {
    override fun validate(target: String) {
        if (!target.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"))) {
            throw IllegalArgumentException("Invalid email format")
        }
    }
}