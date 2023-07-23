package com.example.msger.data

import com.example.msger.data.services.AccountService
import com.example.msger.data.services.AccountServiceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase


class AppContainer {
    private val firebaseAuth: FirebaseAuth = Firebase.auth
    private val firebaseDynamicLinks: FirebaseDynamicLinks = Firebase.dynamicLinks
    val accountService: AccountService = AccountServiceImpl(firebaseAuth,firebaseDynamicLinks)
}