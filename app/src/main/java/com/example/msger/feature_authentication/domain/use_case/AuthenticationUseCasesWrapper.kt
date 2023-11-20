package com.example.msger.feature_authentication.domain.use_case

import com.example.msger.feature_authentication.domain.service.AuthService

class AuthenticationUseCasesWrapper(
    authService: AuthService
) {
    val signInUseCase = SignInUseCase(authService = authService)
    val signUpUseCase = SignUpUseCase(authService = authService)
}