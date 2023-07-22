package com.example.msger.data

import com.example.msger.data.services.AccountService
import com.example.msger.data.services.AccountServiceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AppContainer {
    private val firebaseAuth: FirebaseAuth = Firebase.auth
    val accountService: AccountService = AccountServiceImpl(firebaseAuth)
}