package com.example.msger.feature_chat.domain.use_case

import com.example.msger.core.util.Resource
import com.example.msger.feature_chat.domain.model.Member
import com.example.msger.feature_chat.domain.repository.DatabaseChatRepository
import kotlinx.coroutines.flow.map

class GetChatMembersUseCase(
    private val dbRepository: DatabaseChatRepository
) {
    operator fun invoke(chatId: String) = dbRepository.getChatMembers(chatId = chatId).map {
        when {
            it.isSuccess -> {
                val chatMembers: List<Member> = it.getOrDefault(listOf())
                    .filterNotNull()
                    .flatMap { members ->
                        members.values.sortedBy { member ->
                            member.lastSeen
                        }
                    }
                Resource.Success(chatMembers)
            }

            it.isFailure -> {
                val error = it.exceptionOrNull()
                Resource.Error(error?.message.toString())
            }

            else -> Resource.Loading()
        }
    }
}