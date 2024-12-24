package com.orbit.domain.auth.presentation.dto.request

data class RegisterRequest(
    val email: String,
    val password: String,
    val passwordConfirm: String,
    val name: String
)