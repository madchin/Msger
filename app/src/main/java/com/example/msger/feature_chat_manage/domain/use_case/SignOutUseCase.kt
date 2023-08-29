package com.example.msger.feature_chat_manage.domain.use_case

import com.example.msger.feature_chat_manage.domain.repository.AuthChatManageRepository
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository
import kotlinx.coroutines.coroutineScope


class SignOutUseCase(
    private val authRepository: AuthChatManageRepository,
    private val dbRepository: DatabaseChatManageRepository
) {
    suspend operator fun invoke() {
        authRepository.signOut()
        coroutineScope {
            dbRepository.deleteLocalChats()
        }
    }
}