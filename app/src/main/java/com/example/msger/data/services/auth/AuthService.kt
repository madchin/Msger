package com.example.msger.data.services.auth

import com.example.msger.data.model.User
import kotlinx.coroutines.flow.Flow

interface AuthService {
    val user: Flow<User>

    val isSignedIn: Boolean
    suspend fun createUserWithEmailAndPassword(email: String, password: String)
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    suspend fun resetPassword(email: String)

    suspend fun getUserEmail(): String
    fun signOut()
}