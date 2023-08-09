package com.example.msger.feature_chat.data.data_source.dto

import com.example.msger.feature_chat.domain.model.Member

data class MemberDto(
    val lastSeen: Long? = null,
    val name: String? = null
)

fun MemberDto.toMember() = Member(lastSeen = lastSeen ?: 0, name = name ?: "")
