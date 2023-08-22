package com.example.msger.feature_chat_manage.domain.use_case

import com.example.msger.feature_chat_manage.domain.repository.AuthChatManageRepository
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository
import kotlinx.coroutines.coroutineScope


class SignOutUseCase(
    private val authChatManageRepository: AuthChatManageRepository,
    private val dbRepository: DatabaseChatManageRepository
){
    suspend operator fun invoke() {
        authChatManageRepository.signOut()
        coroutineScope {
            dbRepository.deleteLocalChats()
        }
    }
}