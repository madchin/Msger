package com.example.msger.feature_authentication.domain.use_case

import com.example.msger.feature_authentication.data.repository.AuthRepositoryImpl
import com.example.msger.feature_authentication.domain.repository.AuthRepository

class AuthenticationUseCasesWrapper(
    authRepository: AuthRepository = AuthRepositoryImpl()
) {
    val getEmailFromDeepLinkUseCase = GetEmailFromDeepLinkUseCase(authRepository = authRepository)
    val resetPasswordUseCase = ResetPasswordUseCase(authRepository = authRepository)
    val signInUseCase = SignInUseCase(authRepository = authRepository)
    val signUpUseCase = SignUpUseCase(authRepository = authRepository)
}