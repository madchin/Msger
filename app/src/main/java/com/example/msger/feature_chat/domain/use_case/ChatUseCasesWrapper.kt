package com.example.msger.feature_chat.domain.use_case

import com.example.msger.feature_chat.data.repository.DatabaseChatRepositoryImpl
import com.example.msger.feature_chat.domain.repository.DatabaseChatRepository

class ChatUseCasesWrapper(
    dbRepository: DatabaseChatRepository = DatabaseChatRepositoryImpl()
) {
    val getChatMembersUseCase = GetChatMembersUseCase(dbRepository = dbRepository)
    val getChatMessagesUseCase = GetChatMessagesUseCase(dbRepository = dbRepository)
    val sendMessageUseCase = SendMessageUseCase(dbRepository = dbRepository)
}