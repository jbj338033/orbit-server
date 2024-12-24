package com.orbit.domain.auth.application.validator

import com.orbit.domain.auth.presentation.dto.request.OAuthLoginRequest
import com.orbit.global.common.validator.Validator
import org.springframework.stereotype.Component

@Component
class OAuthLoginValidator: Validator<OAuthLoginRequest> {
    override fun validate(target: OAuthLoginRequest) {
        if (target.code == null && target.idToken == null) {
            throw IllegalArgumentException("Code or idToken is required")
        }

        if (target.code != null && target.idToken != null) {
            throw IllegalArgumentException("Code and idToken cannot be used together")
        }
    }
}