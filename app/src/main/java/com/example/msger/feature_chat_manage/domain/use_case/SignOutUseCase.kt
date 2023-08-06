package com.example.msger.feature_chat_manage.domain.use_case

import com.example.msger.feature_authentication.domain.repository.AuthRepository

class SignOutUseCase(
    private val authRepository: AuthRepository
){
    operator fun invoke() {
        authRepository.signOut()
    }
}