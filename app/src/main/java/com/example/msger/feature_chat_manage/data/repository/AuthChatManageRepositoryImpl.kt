package com.example.msger.feature_chat_manage.data.repository

import com.example.msger.core.data.data_source.remote.auth.Auth
import com.example.msger.core.data.data_source.remote.auth.AuthImpl
import com.example.msger.feature_chat_manage.domain.repository.AuthChatManageRepository

class AuthChatManageRepositoryImpl(
    private val auth: Auth = AuthImpl()
    ) : AuthChatManageRepository {
    override suspend fun signOut() {
        auth.signOut()
    }
}