package com.example.msger.feature_chat.domain.use_case

import com.example.msger.feature_chat.domain.repository.DatabaseChatRepository

class GetChatMessagesUseCase(
    private val dbRepository: DatabaseChatRepository
) {
    operator fun invoke(chatId: String) = dbRepository.getChatMessages(chatId = chatId)
}