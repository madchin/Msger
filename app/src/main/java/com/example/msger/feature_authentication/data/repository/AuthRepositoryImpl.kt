package com.example.msger.feature_authentication.data.repository

import com.example.msger.feature_authentication.data.data_source.Auth
import com.example.msger.feature_authentication.data.data_source.AuthImpl
import com.example.msger.feature_authentication.data.data_source.DeepLinkHandler
import com.example.msger.feature_authentication.data.data_source.DeepLinkHandlerImpl
import com.example.msger.feature_authentication.domain.model.User
import com.example.msger.feature_authentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val auth: Auth = AuthImpl(),
    private val deepLinkHandler: DeepLinkHandler = DeepLinkHandlerImpl()
) : AuthRepository {

    override val user: Flow<User?>
        get() = auth.user

    override val isSignedIn: Boolean
        get() = auth.isSignedIn

    override suspend fun signUp(
        email: String,
        password: String
    ) {
        auth.signUp(email, password)
    }

    override suspend fun signIn(email: String, password: String) {
        auth.signIn(email, password)
    }

    override suspend fun resetPassword(email: String) {
        auth.resetPassword(email)
    }

    override suspend fun getUserEmail(): String? =
        deepLinkHandler.getDynamicLink()?.getQueryParameter("email")
}