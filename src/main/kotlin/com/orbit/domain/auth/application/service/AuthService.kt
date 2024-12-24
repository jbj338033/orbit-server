package com.orbit.domain.auth.application.service

import com.orbit.domain.auth.application.validator.LoginValidator
import com.orbit.domain.auth.application.validator.RefreshValidator
import com.orbit.domain.auth.application.validator.RegisterValidator
import com.orbit.domain.auth.presentation.dto.request.LoginRequest
import com.orbit.domain.auth.presentation.dto.request.RefreshRequest
import com.orbit.domain.auth.presentation.dto.request.RegisterRequest
import com.orbit.domain.user.domain.entity.UserEntity
import com.orbit.domain.user.domain.repository.UserRepository
import com.orbit.global.security.jwt.dto.Jwt
import com.orbit.global.security.jwt.provider.JwtProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val loginValidator: LoginValidator,
    private val registerValidator: RegisterValidator,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val refreshValidator: RefreshValidator
) {
    fun login(request: LoginRequest): Jwt {
        loginValidator.validate(request)

        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("User not found")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("Invalid password")
        }

        return jwtProvider.generateToken(user.email)
    }

    fun register(request: RegisterRequest) {
        registerValidator.validate(request)

        val user = UserEntity(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            name = request.name
        )

        userRepository.save(user)
    }

    fun refresh(request: RefreshRequest): Jwt {
        refreshValidator.validate(request)

        val email = jwtProvider.getSubject(request.refreshToken)

        return jwtProvider.generateToken(email)
    }
}