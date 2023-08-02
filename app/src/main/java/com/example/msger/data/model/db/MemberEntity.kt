package com.example.msger.data.model.db

import com.google.firebase.database.IgnoreExtraProperties
@IgnoreExtraProperties
data class MemberEntity(val lastSeen: Long, val name: String)
