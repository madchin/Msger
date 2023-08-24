package com.example.msger.core.data.data_source.remote.dto

import com.example.msger.core.domain.model.Member

data class ChatMemberDto(
    val lastSeen: Long? = null,
    val username: String? = null,
    val chatName: String? = null,
)

fun ChatMemberDto.toMember() = Member(lastSeen = lastSeen ?: 0, name = username ?: "")
