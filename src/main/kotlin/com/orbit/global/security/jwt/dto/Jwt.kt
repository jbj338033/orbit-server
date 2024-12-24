package com.orbit.global.security.jwt.dto

data class Jwt(
    val accessToken: String,
    val refreshToken: String
)