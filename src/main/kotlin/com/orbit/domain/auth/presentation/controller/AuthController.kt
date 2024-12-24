package com.orbit.domain.auth.presentation.controller

import com.orbit.domain.auth.application.service.AuthService
import com.orbit.domain.auth.presentation.dto.request.LoginRequest
import com.orbit.domain.auth.presentation.dto.request.RefreshRequest
import com.orbit.domain.auth.presentation.dto.request.RegisterRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Auth")
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {
    @Operation(summary = "Login")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    fun login(@RequestBody request: LoginRequest) = authService.login(request)

    @Operation(summary = "Register")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody request: RegisterRequest) = authService.register(request)

    @Operation(summary = "Refresh")
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    fun refresh(@RequestBody request: RefreshRequest) = authService.refresh(request)
}