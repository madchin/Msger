package com.example.msger.feature_authentication.domain.use_case

import com.example.msger.feature_authentication.domain.repository.AuthRepository

class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        authRepository.signUp(email = email, password = password)
    }
}