package com.example.msger.feature_chat.data.repository

import com.example.msger.core.data.data_source.remote.dto.toMember
import com.example.msger.core.domain.model.Member
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat.data.data_source.db.RemoteDatabaseChat
import com.example.msger.feature_chat.data.data_source.db.RemoteDatabaseChatImpl
import com.example.msger.feature_chat.data.data_source.dto.mapToMessages
import com.example.msger.feature_chat.domain.model.Message
import com.example.msger.feature_chat.domain.repository.DatabaseChatRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DatabaseChatRepositoryImpl(
    private val remoteDatabase: RemoteDatabaseChat = RemoteDatabaseChatImpl()
) : DatabaseChatRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getChatMembers(chatId: String): Flow<Resource<List<Member>>> =
        remoteDatabase.getChatMembersUid(chatId = chatId).flatMapLatest {
            flow {
                if (it.isEmpty()) {
                    emit(Resource.Error(message = "Members not exists in this chat"))
                } else {
                    val membersInfo: List<Member> = it.map { uid ->
                        remoteDatabase.getChatMemberInfo(chatId = chatId, userUid = uid)?.toMember() ?: Member(lastSeen = Timestamp.now().seconds, name = "")
                    }
                    emit(Resource.Success(data = membersInfo))
                }
            }
        }

override fun getChatMessages(chatId: String): Flow<Resource<List<Message>>> =
    remoteDatabase.getChatMessages(chatId).map {
        when (it) {
            is Resource.Success -> {
                val messages: List<Message> = it.data?.mapToMessages()
                    ?.sortedBy { message -> message.timestamp } ?: listOf()
                Resource.Success(data = messages)
            }

            is Resource.Error -> {
                Resource.Error(message = it.message ?: "Generic")
            }

            else -> Resource.Loading()
        }
    }.flowOn(Dispatchers.IO)

override suspend fun sendMessage(chatId: String, content: String) {
    remoteDatabase.addMessage(chatId = chatId, content = content)
}
}