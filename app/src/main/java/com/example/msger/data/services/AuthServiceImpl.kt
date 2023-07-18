package com.example.msger.data.services

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthServiceImpl(
    private val auth: FirebaseAuth
) : AuthService {
    override suspend fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }
}