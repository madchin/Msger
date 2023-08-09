package com.example.msger.feature_chat_manage.domain.use_case

import com.example.msger.feature_chat_manage.domain.repository.AuthChatManageRepository


class SignOutUseCase(
    private val authChatManageRepository: AuthChatManageRepository
){
    operator fun invoke() {
        authChatManageRepository.signOut()
    }
}