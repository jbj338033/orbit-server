package com.orbit.domain.user.domain.entity

import com.orbit.domain.user.domain.enums.OAuthProvider
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "password")
    val password: String? = null,

    @Column(name = "profile_image", nullable = false)
    val profileImage: String = DEFAULT_PROFILE_IMAGE,

    @Column(name = "oauth_provider")
    val oAuthProvider: OAuthProvider? = null,

    @Column(name = "oauth_id")
    val oAuthId: String? = null
) {
    companion object {
        const val DEFAULT_PROFILE_IMAGE = "https://orbit.com/default-profile-image.png"
    }
}