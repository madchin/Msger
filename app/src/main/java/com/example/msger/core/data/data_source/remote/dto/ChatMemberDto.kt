package com.example.msger.core.data.data_source.remote.dto

import com.example.msger.core.domain.model.Member
import com.google.firebase.Timestamp

data class ChatMemberDto(
    val lastSeen: Long? = Timestamp.now().seconds,
    val username: String? = null,
    val chatName: String? = null,
)

fun ChatMemberDto.toMember() = Member(lastSeen = lastSeen ?: 0, name = username ?: "")
