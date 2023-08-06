package com.example.msger.feature_authentication.data.data_source

interface CredentialProvider {
    suspend fun getUserEmailAfterPasswordRecover(): String
}