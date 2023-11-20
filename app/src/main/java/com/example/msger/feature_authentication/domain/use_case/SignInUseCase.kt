package com.example.msger.feature_authentication.domain.use_case

import com.example.msger.feature_authentication.domain.service.AuthService
import com.example.msger.feature_authentication.domain.model.UserDto
import com.example.msger.feature_authentication.domain.model.UserSession

class SignInUseCase(
    private val authService: AuthService
) {
    suspend operator fun invoke(user: UserDto): UserSession = authService.signIn(user)

}