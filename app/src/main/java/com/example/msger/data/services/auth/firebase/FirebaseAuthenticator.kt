package com.example.msger.data.services.auth.firebase

import com.example.msger.androidPackageName
import com.example.msger.data.model.User
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseAuthenticator : Authenticator {
    override val auth: FirebaseAuth
        get() = Firebase.auth

    override val isSignedIn: Boolean
        get() = auth.currentUser != null

    override val user: Flow<User> = callbackFlow {
        val listener =
            FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser?.let { User(it.uid) } ?: User())
            }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email.trim(), password).await()
    }

    override suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email.trim(), password).await()
    }

    override fun signOut() = auth.signOut()

    override suspend fun resetPassword(email: String) {
        val scheme = "https://"
        val host = "msger.example.com"
        val url = "$scheme$host/change-password?email=$email"
        val actionCodeSettings = ActionCodeSettings
            .newBuilder()
            .setUrl(url)
            .setAndroidPackageName(androidPackageName, false, null)
            .build()

        auth.sendPasswordResetEmail(email.trim(), actionCodeSettings).await()
    }
}