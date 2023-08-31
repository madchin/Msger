package com.example.msger.feature_authentication.domain.repository

import com.example.msger.feature_authentication.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val user: Flow<User?>

    val isSignedIn: Boolean
    suspend fun signUp(email: String, password: String)
    suspend fun signIn(email: String, password: String)
    suspend fun resetPassword(email: String)

    suspend fun getUserEmail(): String?
}