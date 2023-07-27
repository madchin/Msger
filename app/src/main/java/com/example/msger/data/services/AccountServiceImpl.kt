package com.example.msger.data.services

import android.net.Uri
import com.example.msger.data.model.User
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AccountServiceImpl(
    private val auth: FirebaseAuth,
    private val dynamicLinks: FirebaseDynamicLinks
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
        auth.createUserWithEmailAndPassword(email.trim(), password).await()
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email.trim(), password).await()
    }

    override suspend fun signOutUser() = auth.signOut()

    override suspend fun resetPassword(email: String, actionCodeSettings: ActionCodeSettings) {
        auth.sendPasswordResetEmail(email.trim(), actionCodeSettings).await()
    }

    override suspend fun getDynamicLink(uri: Uri): PendingDynamicLinkData =
        dynamicLinks.getDynamicLink(uri).await()

}