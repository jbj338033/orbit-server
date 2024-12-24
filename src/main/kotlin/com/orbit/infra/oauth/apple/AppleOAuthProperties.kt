package com.orbit.infra.oauth.apple

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("oauth.apple")
data class AppleOAuthProperties(
    val clientId: String,
    val keyId: String,
    val teamId: String,
    val privateKey: String,
)