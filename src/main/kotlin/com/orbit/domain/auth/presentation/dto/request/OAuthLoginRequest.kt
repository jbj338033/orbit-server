package com.orbit.domain.auth.presentation.dto.request

data class OAuthLoginRequest(
    val code: String? = null,
    val idToken: String? = null // Apple
)