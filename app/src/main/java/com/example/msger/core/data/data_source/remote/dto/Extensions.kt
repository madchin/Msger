package com.example.msger.core.data.data_source.remote.dto

import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity
import com.example.msger.feature_chat_manage.domain.model.Chat

fun List<Map<String, MemberDto>?>.mapToChats(): List<Chat> = this.flatMap {
    it?.values?.map { chatInfo ->
        Chat(
            name = chatInfo.name ?: "",
            lastSeen = chatInfo.lastSeen
        )
    } ?: listOf()
}

fun List<Map<String, MemberDto>?>.mapToChatEntities(): List<ChatEntity> = this.flatMap {
    it?.entries?.map { (chatId,chatInfo) ->
        ChatEntity(
            name = chatInfo.name ?: "",
            lastSeen = chatInfo.lastSeen,
            chatId = chatId
        )
    } ?: listOf()
}