package com.example.msger.feature_onboarding.data.data_source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticatorOnboardingImpl : AuthenticatorOnboarding {
    private val auth: FirebaseAuth
        get() = Firebase.auth

    override val isSignedIn: Boolean
        get() = auth.currentUser != null

}