package com.orbit.domain.auth.presentation.controller

import com.orbit.domain.auth.application.service.OAuthService
import com.orbit.domain.auth.presentation.dto.request.OAuthLoginRequest
import com.orbit.domain.user.domain.enums.OAuthProvider
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "OAuth")
@RestController
@RequestMapping("/oauth")
class OAuthController(
    private val oAuthService: OAuthService
) {
    @Operation(summary = "Login")
    @PostMapping("/login/{provider}")
    @ResponseStatus(HttpStatus.CREATED)
    fun login(@PathVariable provider: OAuthProvider, @RequestBody request: OAuthLoginRequest) = oAuthService.login(provider, request)
}