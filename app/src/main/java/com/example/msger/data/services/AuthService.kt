package com.example.msger.data.services

interface AuthService {
    suspend fun createUserWithEmailAndPassword(email: String, password: String)
    suspend fun signInWithEmailAndPassword(email: String, password: String)
}