package com.orbit.domain.auth.application.service

import com.orbit.domain.auth.application.validator.OAuthLoginValidator
import com.orbit.domain.auth.presentation.dto.request.OAuthLoginRequest
import com.orbit.domain.user.domain.entity.UserEntity
import com.orbit.domain.user.domain.enums.OAuthProvider
import com.orbit.domain.user.domain.repository.UserRepository
import com.orbit.global.security.jwt.dto.Jwt
import com.orbit.global.security.jwt.provider.JwtProvider
import com.orbit.infra.oauth.apple.AppleOAuthClient
import com.orbit.infra.oauth.google.GoogleOAuthClient
import com.orbit.infra.oauth.kakao.KakaoOAuthClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
@Transactional
class OAuthService(
    private val kakaoOAuthClient: KakaoOAuthClient,
    private val googleOAuthClient: GoogleOAuthClient,
    private val appleOAuthClient: AppleOAuthClient,
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val oAuthLoginValidator: OAuthLoginValidator
) {
    fun login(provider: OAuthProvider, request: OAuthLoginRequest): Jwt {
        when (provider) {
            OAuthProvider.APPLE -> {
                if (request.idToken == null) {
                    throw IllegalArgumentException("idToken is required")
                }
            }
            else -> {
                if (request.code == null) {
                    throw IllegalArgumentException("code is required")
                }
            }
        }

        oAuthLoginValidator.validate(request)

        val info = when (provider) {
            OAuthProvider.KAKAO -> {
                val token = kakaoOAuthClient.getToken(request.code!!)
                kakaoOAuthClient.getUserInfo(token.accessToken).toOAuthUserInfo()
            }
            OAuthProvider.GOOGLE -> {
                val token = googleOAuthClient.getToken(request.code!!)
                googleOAuthClient.getUserInfo(token.accessToken).toOAuthUserInfo()
            }
            OAuthProvider.APPLE -> {
                appleOAuthClient.verifyIdentityToken(request.idToken!!).toOAuthUserInfo()
            }
        }

        val user = userRepository.findByEmail(info.email) ?: userRepository.save(UserEntity(
            email = info.email ?: throw IllegalArgumentException("Email is required"),
            name = info.name ?: "User${Random.nextInt(10000000, 100000000)}",
            oAuthProvider = provider,
            oAuthId = info.id,
            profileImage = info.profileImage ?: UserEntity.DEFAULT_PROFILE_IMAGE
        ))

        return jwtProvider.generateToken(user.email)
    }
}