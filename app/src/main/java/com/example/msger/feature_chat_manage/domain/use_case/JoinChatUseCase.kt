package com.example.msger.feature_chat_manage.domain.use_case

import com.example.msger.core.util.GenericException
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository

class JoinChatUseCase(
    private val dbRepository: DatabaseChatManageRepository
) {
    suspend operator fun invoke(username: String, chatId: String) {
        try {
            dbRepository.joinChat(username, chatId)
        }
        catch(e: IllegalArgumentException) {
            throw Exception("Chat Id: $chatId doesn\'t exists")
        }
        catch(e: Throwable) {
            throw GenericException()
        }
    }
}