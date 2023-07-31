package com.example.msger.data.services.auth.firebase

import com.example.msger.data.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow

interface Authenticator {
    val auth: FirebaseAuth
    val isSignedIn: Boolean
    val user: Flow<User>

    suspend fun createUserWithEmailAndPassword(email: String, password: String)
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    suspend fun resetPassword(email: String)
    fun signOut()

}