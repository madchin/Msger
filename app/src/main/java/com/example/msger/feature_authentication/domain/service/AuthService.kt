package com.example.msger.feature_authentication.domain.service

import com.example.msger.feature_authentication.domain.model.UserDto
import com.example.msger.feature_authentication.domain.model.UserSession
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("sign-in")
    suspend fun signIn(@Body user: UserDto): UserSession

    @POST("sign-up")
    suspend fun signUp(@Body user: UserDto): UserSession
}