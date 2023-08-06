package com.example.msger.feature_authentication.domain.use_case

import com.example.msger.feature_authentication.domain.repository.AuthRepository

class SignInUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        authRepository.signIn(email = email, password = password)
    }
}