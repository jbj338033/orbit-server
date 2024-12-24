package com.orbit.domain.auth.application.validator

import com.orbit.domain.auth.presentation.dto.request.LoginRequest
import com.orbit.global.common.validator.Validator
import org.springframework.stereotype.Component

@Component
class LoginValidator(
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator
): Validator<LoginRequest> {
    override fun validate(target: LoginRequest) {
        emailValidator.validate(target.email)
        passwordValidator.validate(target.password)
    }
}