package com.orbit.infra.oauth.apple

import com.orbit.infra.oauth.apple.dto.AppleUserInfo
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class AppleOAuthClient(
    private val appleOAuthProperties: AppleOAuthProperties
) {
    private val webClient: WebClient = WebClient.builder()
        .baseUrl("https://appleid.apple.com")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()


    fun verifyIdentityToken(identityToken: String): AppleUserInfo {
        // Apple의 경우 identity token을 직접 검증
//        val jwt = JWT.decode(identityToken)
        // JWT 검증 로직...

        return AppleUserInfo(
            "appleId",
            "appleEmail"
//            id = jwt.subject,
//            email = jwt.claims["email"]?.asString()
        )
    }

    private fun createClientSecret(): String {
        // Apple client secret 생성 로직
        // JWT 생성...
        return "generated.client.secret"
    }
}