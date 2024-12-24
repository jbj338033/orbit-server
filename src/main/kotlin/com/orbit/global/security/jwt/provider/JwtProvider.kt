package com.orbit.global.security.jwt.provider

import com.orbit.domain.user.domain.repository.UserRepository
import com.orbit.global.security.details.CustomUserDetails
import com.orbit.global.security.jwt.config.JwtProperties
import com.orbit.global.security.jwt.dto.Jwt
import com.orbit.global.security.jwt.enums.JwtType
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date
import javax.crypto.spec.SecretKeySpec

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val userRepository: UserRepository
) {
    private val key = SecretKeySpec(
        jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8),
        Jwts.SIG.HS512.key().build().algorithm
    )

    fun generateToken(subject: String) = Jwt(
        accessToken = generateAccessToken(subject),
        refreshToken = generateRefreshToken(subject)
    )

    fun getSubject(token: String): String = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload.subject

    fun getAuthentication(token: String): Authentication {
        val user = userRepository.findByEmail(getSubject(token))
            ?: throw IllegalArgumentException("User not found")
        val details = CustomUserDetails(user)

        return UsernamePasswordAuthenticationToken(details, null, details.authorities)
    }

    fun extractToken(request: HttpServletRequest) = request.getHeader("Authorization")?.removePrefix("Bearer ")

    fun getType(token: String) = JwtType.valueOf(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).header.type)

    private fun generateAccessToken(subject: String) = Date().let {
        Jwts.builder()
            .header()
            .type(JwtType.ACCESS.name)
            .and()
            .subject(subject)
            .issuedAt(it)
            .expiration(it.apply { time += jwtProperties.accessTokenExpiration })
            .signWith(key)
            .compact()
    }

    private fun generateRefreshToken(subject: String) = Date().let {
        Jwts.builder()
            .header()
            .type(JwtType.REFRESH.name)
            .and()
            .subject(subject)
            .issuedAt(it)
            .expiration(it.apply { time += jwtProperties.refreshTokenExpiration })
            .signWith(key)
            .compact()
    }
}