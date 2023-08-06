package com.example.msger.feature_authentication.data

import com.example.msger.data.services.db.DbService
import com.example.msger.data.services.db.DbServiceImpl
import com.example.msger.data.services.db.firebase.Database
import com.example.msger.data.services.db.firebase.FirebaseDb
import com.example.msger.feature_authentication.data.data_source.Authenticator
import com.example.msger.feature_authentication.data.data_source.AuthenticatorImpl
import com.example.msger.feature_authentication.data.data_source.CredentialProvider
import com.example.msger.feature_authentication.data.data_source.CredentialProviderImpl
import com.example.msger.feature_authentication.data.repository.AuthRepositoryImpl
import com.example.msger.feature_authentication.domain.repository.AuthRepository
import com.example.msger.feature_authentication.domain.use_case.GetEmailFromRecoverPasswordRedirectionUseCase
import com.example.msger.feature_authentication.domain.use_case.ResetPasswordUseCase
import com.example.msger.feature_authentication.domain.use_case.SignInUseCase
import com.example.msger.feature_authentication.domain.use_case.SignUpUseCase

class AppContainer {
    private val auth: Authenticator = AuthenticatorImpl()
    private val credentialProvider: CredentialProvider = CredentialProviderImpl()
    private val firebaseDatabase: Database = FirebaseDb()

    val dbService: DbService = DbServiceImpl(firebaseDatabase)
    val authRepository: AuthRepository = AuthRepositoryImpl(auth, credentialProvider)
    val resetPasswordUseCase: ResetPasswordUseCase = ResetPasswordUseCase(authRepository = authRepository)
    val signInUseCase: SignInUseCase = SignInUseCase(authRepository = authRepository)
    val getEmailFromRecoverPasswordRedirectionUseCase: GetEmailFromRecoverPasswordRedirectionUseCase =
        GetEmailFromRecoverPasswordRedirectionUseCase(authRepository = authRepository)
    val signUpUseCase: SignUpUseCase = SignUpUseCase(authRepository = authRepository)
}