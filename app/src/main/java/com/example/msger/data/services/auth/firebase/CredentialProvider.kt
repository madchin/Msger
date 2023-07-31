package com.example.msger.data.services.auth.firebase

interface CredentialProvider {
    suspend fun getUserEmail(): String
}