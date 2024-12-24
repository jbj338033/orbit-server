package com.orbit.infra.oauth.kakao.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.orbit.domain.auth.domain.model.OAuthUserInfo

data class KakaoUserInfo(
    val id: Long,
    @JsonProperty("kakao_account") val kakaoAccount: KakaoAccount
) {
    data class KakaoAccount(
        val email: String?,
        val profile: Profile?
    ) {
        data class Profile(
            val nickname: String?,
            @JsonProperty("profile_image_url") val profileImageUrl: String?
        )
    }

    fun toOAuthUserInfo() = OAuthUserInfo(
        id = id.toString(),
        email = kakaoAccount.email,
        name = kakaoAccount.profile?.nickname,
        profileImage = kakaoAccount.profile?.profileImageUrl
    )
}