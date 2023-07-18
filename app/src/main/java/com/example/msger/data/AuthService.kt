package com.example.msger.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthService {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onResult: (Task<AuthResult>) -> Unit
    )

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onResult: (Task<AuthResult>) -> Unit
    )

    suspend fun signInWithGoogle()
    suspend fun getAuthenticatedUser(): FirebaseUser?
}