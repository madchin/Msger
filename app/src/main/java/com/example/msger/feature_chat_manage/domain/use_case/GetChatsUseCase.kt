package com.example.msger.feature_chat_manage.domain.use_case

import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository

class GetChatsUseCase(
    private val dbRepository: DatabaseChatManageRepository
) {
    operator fun invoke() = dbRepository.getAllChats()

}