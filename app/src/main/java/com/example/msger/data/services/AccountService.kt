package com.example.msger.data.services

import android.net.Uri
import com.example.msger.data.model.User
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val user: Flow<User>

    val isSignedIn: Boolean
    suspend fun createUserWithEmailAndPassword(email: String, password: String)
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    suspend fun resetPassword(email: String, actionCodeSettings: ActionCodeSettings)

    suspend fun getDynamicLink(uri: Uri): PendingDynamicLinkData
    suspend fun signOutUser()
}