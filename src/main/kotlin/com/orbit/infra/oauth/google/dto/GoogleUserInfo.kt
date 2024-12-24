package com.orbit.infra.oauth.google.dto

import com.orbit.domain.auth.domain.model.OAuthUserInfo

data class GoogleUserInfo(
    val sub: String,
    val email: String,
    val name: String?,
    val picture: String?,
) {
    fun toOAuthUserInfo() = OAuthUserInfo(
        id = sub,
        email = email,
        name = name,
        profileImage = picture
    )
}