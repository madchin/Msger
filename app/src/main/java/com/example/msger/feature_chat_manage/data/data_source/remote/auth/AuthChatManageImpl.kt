package com.example.msger.feature_chat_manage.data.data_source.remote.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AuthChatManageImpl : AuthChatManage {
    override fun signOut() {
        Firebase.auth.signOut()
    }
}