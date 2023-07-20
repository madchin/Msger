package com.example.msger.data.services

import com.example.msger.data.model.User
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthService {
    val user: Flow<User>

    val isSignedIn: Boolean
    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthResult
    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult

    suspend fun signOutUser()
}