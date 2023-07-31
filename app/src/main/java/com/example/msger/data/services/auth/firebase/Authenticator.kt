package com.example.msger.data.services.auth.firebase

import com.example.msger.data.model.User
import kotlinx.coroutines.flow.Flow

interface Authenticator {
    val isSignedIn: Boolean
    val user: Flow<User>

    suspend fun signUp(email: String, password: String)
    suspend fun signIn(email: String, password: String)
    suspend fun resetPassword(email: String)
    fun signOut()

}