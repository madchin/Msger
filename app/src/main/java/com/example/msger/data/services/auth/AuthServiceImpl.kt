package com.example.msger.data.services.auth

import com.example.msger.data.model.User
import com.example.msger.data.services.auth.firebase.Authenticator
import com.example.msger.data.services.auth.firebase.CredentialProvider
import kotlinx.coroutines.flow.Flow

class AuthServiceImpl(
    private val auth: Authenticator,
    private val credentialProvider: CredentialProvider
) : AuthService {

    override val user: Flow<User>
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
        credentialProvider.getUserEmail()
}