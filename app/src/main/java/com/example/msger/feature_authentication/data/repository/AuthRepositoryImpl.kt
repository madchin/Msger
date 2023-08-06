package com.example.msger.feature_authentication.data.repository

import com.example.msger.feature_authentication.data.data_source.Authenticator
import com.example.msger.feature_authentication.data.data_source.CredentialProvider
import com.example.msger.feature_authentication.domain.model.User
import com.example.msger.feature_authentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val auth: Authenticator,
    private val credentialProvider: CredentialProvider
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

    override fun signOut() = auth.signOut()

    override suspend fun resetPassword(email: String) {
        auth.resetPassword(email)
    }

    override suspend fun getUserEmail(): String =
        credentialProvider.getUserEmailAfterPasswordRecover()
}