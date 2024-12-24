package com.orbit.infra.oauth.kakao

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth.kakao")
data class KakaoOAuthProperties(
    var clientId: String,
    var clientSecret: String,
    var redirectUri: String
)