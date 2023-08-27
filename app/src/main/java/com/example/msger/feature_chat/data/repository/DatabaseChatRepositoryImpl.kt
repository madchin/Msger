package com.example.msger.feature_chat.data.repository

import com.example.msger.core.data.data_source.remote.dto.mapToMembers
import com.example.msger.core.domain.model.Member
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat.data.data_source.db.RemoteDatabaseChat
import com.example.msger.feature_chat.data.data_source.dto.mapToMessages
import com.example.msger.feature_chat.domain.model.Message
import com.example.msger.feature_chat.domain.repository.DatabaseChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseChatRepositoryImpl(
    private val dbRepository: RemoteDatabaseChat
) : DatabaseChatRepository {
    override fun getChatMembers(chatId: String): Flow<Resource<List<Member>>> =
        dbRepository.getChatMembers(chatId).map {
            when (it) {
                is Resource.Success -> {
                    val chatMembers: List<Member> = it.data?.mapToMembers() ?: listOf()
                    Resource.Success(data = chatMembers)
                }

                is Resource.Error -> {
                    Resource.Error(it.message ?: "Generic")
                }

                else -> Resource.Loading()
            }
        }

    override fun getChatMessages(chatId: String): Flow<Resource<List<Message>>> =
        dbRepository.getChatMessages(chatId).map {
            when (it) {
                is Resource.Success -> {
                    val messages: List<Message> = it.data?.mapToMessages()
                        ?.sortedBy { message -> message.timestamp }  ?: listOf()
                    Resource.Success(data = messages)
                }

                is Resource.Error -> {
                    Resource.Error(message = it.message ?: "Generic")
                }

                else -> Resource.Loading()
            }
        }
}