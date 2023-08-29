package com.example.msger.feature_chat.data.data_source.dto

import com.example.msger.feature_chat.domain.model.Message

fun List<Map<String, MessageDto>?>.mapToMessages(): List<Message> = this.flatMap {
    it?.entries?.map { (messageKey: String, message: MessageDto) ->
        Message(sender = message.sender ?: "", content = message.content ?: "", timestamp = message.timestamp, key = messageKey)
    } ?: listOf()
}