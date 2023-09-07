package com.example.msger.core.data.data_source.remote.auth

import com.example.msger.feature_authentication.domain.model.User
import kotlinx.coroutines.flow.Flow

interface Auth {
    val isSignedIn: Boolean
    val user: Flow<User?>

    suspend fun signUp(email: String, password: String)
    suspend fun signIn(email: String, password: String)
    suspend fun resetPassword(email: String)

    suspend fun signOut()

}