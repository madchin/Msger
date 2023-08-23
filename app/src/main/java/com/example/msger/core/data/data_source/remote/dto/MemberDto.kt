package com.example.msger.core.data.data_source.remote.dto

import com.example.msger.core.domain.model.Member

data class MemberDto(
    val lastSeen: Long? = null,
    val name: String? = null
)

fun MemberDto.toMember() = Member(lastSeen = lastSeen ?: 0, name = name ?: "")
