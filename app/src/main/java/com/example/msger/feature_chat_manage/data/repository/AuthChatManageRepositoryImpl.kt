package com.example.msger.feature_chat_manage.data.repository

import com.example.msger.feature_chat_manage.data.data_source.remote.auth.AuthChatManage
import com.example.msger.feature_chat_manage.domain.repository.AuthChatManageRepository

class AuthChatManageRepositoryImpl(private val authChatManage: AuthChatManage) : AuthChatManageRepository {
    override fun signOut() {
        authChatManage.signOut()
    }
}