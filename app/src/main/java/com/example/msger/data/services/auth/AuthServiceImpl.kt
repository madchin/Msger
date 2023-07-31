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

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email.trim(), password)
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email.trim(), password)
    }

    override fun signOut() = auth.signOut()

    override suspend fun resetPassword(email: String) {
        auth.resetPassword(email.trim())
    }

    override suspend fun getUserEmail(): String =
        credentialProvider.getUserEmail()
}