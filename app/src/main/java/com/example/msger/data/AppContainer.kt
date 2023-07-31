package com.example.msger.data

import com.example.msger.data.services.auth.AuthService
import com.example.msger.data.services.auth.AuthServiceImpl
import com.example.msger.data.services.auth.firebase.Authenticator
import com.example.msger.data.services.auth.firebase.CredentialProvider
import com.example.msger.data.services.auth.firebase.FirebaseAuthenticator
import com.example.msger.data.services.auth.firebase.FirebaseCredentialProvider
import com.example.msger.data.services.db.DbService
import com.example.msger.data.services.db.DbServiceImpl
import com.example.msger.data.services.db.firebase.Database
import com.example.msger.data.services.db.firebase.FirebaseDb

class AppContainer {
    private val auth: Authenticator = FirebaseAuthenticator()
    private val credentialProvider: CredentialProvider = FirebaseCredentialProvider()
    private val firebaseDatabase: Database = FirebaseDb()

    val dbService: DbService = DbServiceImpl(firebaseDatabase)
    val authService: AuthService = AuthServiceImpl(auth, credentialProvider)
}