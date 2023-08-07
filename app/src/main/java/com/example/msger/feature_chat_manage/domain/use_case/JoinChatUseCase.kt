package com.example.msger.feature_chat_manage.domain.use_case

import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository

class JoinChatUseCase(
    private val dbRepository: DatabaseChatManageRepository
) {
    suspend operator fun invoke(username: String, chatId: String) {
        dbRepository.joinChat(username, chatId)
    }
}