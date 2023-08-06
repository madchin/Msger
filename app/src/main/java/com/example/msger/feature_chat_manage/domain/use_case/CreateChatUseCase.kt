package com.example.msger.feature_chat_manage.domain.use_case

import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository

class CreateChatUseCase(
    private val dbRepository: DatabaseChatManageRepository
) {
    suspend operator fun invoke(username: String, chat: Chat): String =
        dbRepository.createChat(username = username, chat = chat)
}