package com.orbit.domain.auth.application.validator

import com.orbit.domain.auth.presentation.dto.request.RegisterRequest
import com.orbit.domain.user.domain.repository.UserRepository
import com.orbit.global.common.validator.Validator
import org.springframework.stereotype.Component

@Component
class RegisterValidator(
    private val nameValidator: NameValidator,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val userRepository: UserRepository
): Validator<RegisterRequest> {
    override fun validate(target: RegisterRequest) {
        nameValidator.validate(target.name)
        emailValidator.validate(target.email)
        passwordValidator.validate(target.password)

        if (target.password != target.passwordConfirm) {
            throw IllegalArgumentException("Password and password confirm do not match")
        }

        if (userRepository.existsByEmail(target.email)) {
            throw IllegalArgumentException("Email already exists")
        }
    }
}