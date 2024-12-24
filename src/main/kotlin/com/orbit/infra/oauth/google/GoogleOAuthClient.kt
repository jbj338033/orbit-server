package com.orbit.infra.oauth.google

import com.orbit.infra.oauth.google.dto.GoogleTokenResponse
import com.orbit.infra.oauth.google.dto.GoogleUserInfo
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GoogleOAuthClient(
    private val googleOAuthProperties: GoogleOAuthProperties
) {
    private val webClient: WebClient = WebClient.builder()
        .baseUrl("https://oauth2.googleapis.com")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    fun getToken(code: String): GoogleTokenResponse {
        return webClient.post()
            .uri("/token")
            .bodyValue(
                mapOf(
                    "grant_type" to "authorization_code",
                    "client_id" to googleOAuthProperties.clientId,
                    "client_secret" to googleOAuthProperties.clientSecret,
                    "code" to code,
                    "redirect_uri" to googleOAuthProperties.redirectUri
                )
            )
            .retrieve()
            .bodyToMono(GoogleTokenResponse::class.java)
            .block() ?: throw IllegalArgumentException("Failed to get Google token")
    }

    fun getUserInfo(accessToken: String): GoogleUserInfo {
        return webClient.get()
            .uri("https://www.googleapis.com/oauth2/v3/userinfo")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .bodyToMono(GoogleUserInfo::class.java)
            .block() ?: throw IllegalArgumentException("Failed to get Google user info")
    }
}