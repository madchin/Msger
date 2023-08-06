package com.example.msger.feature_chat_manage.domain.use_case

import com.example.msger.core.util.Resource
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository
import kotlinx.coroutines.flow.map

class GetChatsUseCase(
    private val dbRepository: DatabaseChatManageRepository
) {
    operator fun invoke() = dbRepository.chats.map {
        when {
            it.isSuccess -> Resource.Success(it.getOrDefault(listOf()))
            it.isFailure -> {
                val error = it.exceptionOrNull()
                Resource.Error(error?.message.toString())
            }

            else -> Resource.Loading()
        }
    }
}