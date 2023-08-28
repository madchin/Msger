package com.example.msger.feature_chat.domain.use_case

import com.example.msger.feature_chat.domain.repository.DatabaseChatRepository

class SendMessageUseCase(
    private val dbRepository: DatabaseChatRepository
) {
    suspend operator fun invoke(chatId: String, content: String) = dbRepository.sendMessage(chatId = chatId, content = content)
}