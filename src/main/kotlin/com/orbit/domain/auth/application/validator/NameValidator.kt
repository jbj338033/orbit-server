package com.orbit.domain.auth.application.validator

import com.orbit.global.common.validator.Validator
import org.springframework.stereotype.Component

@Component
class NameValidator: Validator<String> {
    override fun validate(target: String) {
        if (target.length < 2 || target.length > 20) {
            throw IllegalArgumentException("Name must be between 2 and 20 characters")
        }
    }
}