package com.orbit.infra.oauth.kakao

import com.orbit.infra.oauth.kakao.dto.KakaoTokenResponse
import com.orbit.infra.oauth.kakao.dto.KakaoUserInfo
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class KakaoOAuthClient(
    private val kakaoOAuthProperties: KakaoOAuthProperties
) {
    private val webClient: WebClient = WebClient.builder()
        .baseUrl("https://kauth.kakao.com")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    fun getToken(code: String): KakaoTokenResponse {
        return webClient.post()
            .uri("/oauth/token")
            .bodyValue(
                mapOf(
                    "grant_type" to "authorization_code",
                    "client_id" to kakaoOAuthProperties.clientId,
                    "client_secret" to kakaoOAuthProperties.clientSecret,
                    "code" to code,
                    "redirect_uri" to kakaoOAuthProperties.redirectUri
                )
            )
            .retrieve()
            .bodyToMono(KakaoTokenResponse::class.java)
            .block() ?: throw IllegalArgumentException("Failed to get Kakao token")
    }

    fun getUserInfo(accessToken: String): KakaoUserInfo {
        return webClient.get()
            .uri("https://kapi.kakao.com/v2/user/me")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .bodyToMono(KakaoUserInfo::class.java)
            .block() ?: throw IllegalArgumentException("Failed to get Kakao user info")
    }
}