package com.orbit.domain.auth.presentation.dto.request

data class LoginRequest(
    val email: String,
    val password: String
)