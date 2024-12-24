package com.orbit.domain.user.domain.repository

import com.orbit.domain.user.domain.entity.UserEntity
import com.orbit.domain.user.domain.enums.OAuthProvider
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<UserEntity, UUID> {
    fun findByEmail(email: String?): UserEntity?
    fun findByOAuthProviderAndOAuthId(oAuthProvider: OAuthProvider, oAuthId: String): UserEntity?

    fun existsByEmail(email: String): Boolean
}