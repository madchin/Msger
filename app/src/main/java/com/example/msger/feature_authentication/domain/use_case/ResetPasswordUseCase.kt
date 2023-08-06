package com.example.msger.feature_authentication.domain.use_case

import com.example.msger.feature_authentication.domain.repository.AuthRepository

class ResetPasswordUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String) {
        authRepository.resetPassword(email)
    }
}