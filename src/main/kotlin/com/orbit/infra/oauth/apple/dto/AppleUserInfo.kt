package com.orbit.infra.oauth.apple.dto

import com.orbit.domain.auth.domain.model.OAuthUserInfo

data class AppleUserInfo(
    val id: String,
    val email: String?,
) {
    fun toOAuthUserInfo() = OAuthUserInfo(
        id = id,
        email = email,
        name = null,
        profileImage = null
    )
}