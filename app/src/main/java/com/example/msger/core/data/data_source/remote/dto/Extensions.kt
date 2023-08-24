package com.example.msger.core.data.data_source.remote.dto

import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity
import com.example.msger.feature_chat_manage.domain.model.Chat

fun List<Map<String, ChatMemberDto>?>.mapToChats(): List<Chat> = this.flatMap {
    it?.entries?.map { (chatId: String, chatInfo: ChatMemberDto) ->
        Chat(
            name = chatInfo.chatName ?: "",
            lastSeen = chatInfo.lastSeen,
            id = chatId
        )
    } ?: listOf()
}

fun List<Map<String, ChatMemberDto>?>.mapToChatEntities(): List<ChatEntity> = this.flatMap {
    it?.entries?.map { (chatId,chatInfo) ->
        ChatEntity(
            chatName = chatInfo.chatName ?: "",
            lastSeen = chatInfo.lastSeen,
            chatId = chatId
        )
    } ?: listOf()
}