package com.orbit.global.security.details

import com.orbit.domain.user.domain.entity.UserEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val user: UserEntity
): UserDetails {
    override fun getUsername() = user.email
    override fun getPassword() = user.password
    override fun getAuthorities() = listOf(SimpleGrantedAuthority("ROLE_USER"))
}