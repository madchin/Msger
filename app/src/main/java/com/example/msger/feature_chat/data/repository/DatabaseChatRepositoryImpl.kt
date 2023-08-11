package com.example.msger.feature_chat.data.repository

import com.example.msger.core.util.Resource
import com.example.msger.feature_chat.data.data_source.db.RemoteDatabaseChat
import com.example.msger.feature_chat.data.data_source.dto.toMember
import com.example.msger.feature_chat.domain.model.Member
import com.example.msger.feature_chat.domain.repository.DatabaseChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseChatRepositoryImpl(
    private val dbRepository: RemoteDatabaseChat
) : DatabaseChatRepository {
    override fun getChatMembers(chatId: String): Flow<Resource<List<Member>>> =
        dbRepository.getChatMembers(chatId).map {
            when {
                it.isSuccess -> {
                    val chatMembers: List<Member> = it.getOrDefault(listOf())
                        .filterNotNull()
                        .flatMap { members ->
                            members.values.sortedBy { member ->
                                member.lastSeen
                            }.map { memberDto -> memberDto.toMember() }
                        }
                    Resource.Success(chatMembers)
                }

                it.isFailure -> {
                    val error: Throwable? = it.exceptionOrNull()
                    Resource.Error(error?.message.toString())
                }

                else -> Resource.Loading()
            }
        }
}