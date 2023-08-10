package com.example.msger.feature_chat_manage.data.data_source.remote.dto

import com.google.firebase.Timestamp

data class MemberDto(
    val lastSeen: Long = Timestamp.now().seconds,
    val name: String
)
