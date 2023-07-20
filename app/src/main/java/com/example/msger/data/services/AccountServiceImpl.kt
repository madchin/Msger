package com.example.msger.data.services

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import com.example.msger.data.model.User
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AccountServiceImpl(
    private val auth: FirebaseAuth,
    private val dynamicLink: FirebaseDynamicLinks
) : AccountService {

    override val user: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override val isSignedIn: Boolean
        get() = auth.currentUser != null

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOutUser() = auth.signOut()

    override suspend fun resetPassword(email: String, actionCodeSettings: ActionCodeSettings) {
        auth.sendPasswordResetEmail(email, actionCodeSettings).await()
    }


    override suspend fun getDynamicLink() {
        dynamicLink.getDynamicLink(Intent(ACTION_VIEW)).await()
    }
}