package com.example.msger.core.data.data_source.remote.dto

import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity

fun List<Map<String, ChatMemberDto>?>.mapToChatEntities(): List<ChatEntity> = this.flatMap {
    it?.entries?.map { (chatId, chatInfo) ->
        ChatEntity(
            chatName = chatInfo.chatName ?: "",
            lastSeen = chatInfo.lastSeen,
            username = chatInfo.username,
            chatId = chatId
        )
    } ?: listOf()
}
