package com.example.msger.feature_authentication.data.data_source

import com.example.msger.feature_authentication.domain.model.User
import kotlinx.coroutines.flow.Flow

interface Auth {
    val isSignedIn: Boolean
    val user: Flow<User?>

    suspend fun signUp(email: String, password: String)
    suspend fun signIn(email: String, password: String)
    suspend fun resetPassword(email: String)
    fun signOut()

}