package com.example.msger.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthServiceImpl(
    private val auth: FirebaseAuth
) : AuthService {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onResult: (Task<AuthResult>) -> Unit
    ) {
        auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it) }
    }


    override suspend fun getAuthenticatedUser() = auth.currentUser


    override suspend fun signInWithEmailAndPassword(email: String, password: String, onResult: (Task<AuthResult>) -> Unit) {
        auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it) }
    }

    override suspend fun signInWithGoogle() {
    }
}