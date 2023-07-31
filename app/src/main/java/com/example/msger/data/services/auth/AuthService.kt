package com.example.msger.data.services.auth

import com.example.msger.data.model.User
import kotlinx.coroutines.flow.Flow

interface AuthService {
    val user: Flow<User>

    val isSignedIn: Boolean
    suspend fun signUp(email: String, password: String)
    suspend fun signIn(email: String, password: String)
    suspend fun resetPassword(email: String)

    suspend fun getUserEmail(): String
    fun signOut()
}