package com.example.msger.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AppContainer {
    private val firebaseAuth = Firebase.auth

    val authService: AuthService = AuthServiceImpl(firebaseAuth)
}