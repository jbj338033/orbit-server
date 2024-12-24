package com.orbit.domain.auth.domain.model

data class OAuthUserInfo(
    val id: String,
    val email: String?,
    val name: String?,
    val profileImage: String?
)