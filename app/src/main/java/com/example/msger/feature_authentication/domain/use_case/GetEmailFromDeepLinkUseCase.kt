package com.example.msger.feature_authentication.domain.use_case

import com.example.msger.feature_authentication.domain.repository.AuthRepository

class GetEmailFromDeepLinkUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): String? =
        authRepository.getUserEmail()
}