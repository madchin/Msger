package com.example.msger.data.services

import com.example.msger.data.model.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthServiceImpl(
    private val auth: FirebaseAuth
) : AuthService {

    override val user: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult = auth.createUserWithEmailAndPassword(email, password).await()

    override suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult =
        auth.signInWithEmailAndPassword(email, password).await()

    override suspend fun signOutUser() = auth.signOut()
}