package com.example.msger.core.data

import com.example.msger.data.services.db.DbService
import com.example.msger.data.services.db.DbServiceImpl
import com.example.msger.data.services.db.firebase.Database
import com.example.msger.data.services.db.firebase.DatabaseImpl
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
import com.example.msger.feature_chat_manage.data.data_source.db.DatabaseChatManage
import com.example.msger.feature_chat_manage.data.data_source.db.DatabaseChatManageImpl
import com.example.msger.feature_chat_manage.data.repository.DatabaseChatManageRepositoryImpl
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository
import com.example.msger.feature_chat_manage.domain.use_case.CreateChatUseCase
import com.example.msger.feature_chat_manage.domain.use_case.GetChatsUseCase
import com.example.msger.feature_chat_manage.domain.use_case.SignOutUseCase

class AppContainer {
    private val auth: Authenticator = AuthenticatorImpl()
    private val credentialProvider: CredentialProvider = CredentialProviderImpl()
    private val firebaseDatabase: Database = DatabaseImpl()
    private val databaseChatManage: DatabaseChatManage = DatabaseChatManageImpl()

    val databaseChatManageRepository: DatabaseChatManageRepository = DatabaseChatManageRepositoryImpl(databaseChatManage)
    val dbService: DbService = DbServiceImpl(database = firebaseDatabase)
    val authRepository: AuthRepository = AuthRepositoryImpl(auth, credentialProvider)
    val resetPasswordUseCase: ResetPasswordUseCase = ResetPasswordUseCase(authRepository = authRepository)
    val signInUseCase: SignInUseCase = SignInUseCase(authRepository = authRepository)
    val getEmailFromRecoverPasswordRedirectionUseCase: GetEmailFromRecoverPasswordRedirectionUseCase =
        GetEmailFromRecoverPasswordRedirectionUseCase(authRepository = authRepository)
    val signUpUseCase: SignUpUseCase = SignUpUseCase(authRepository = authRepository)
    val signOutUseCase: SignOutUseCase = SignOutUseCase(authRepository = authRepository)
    val getChatsUseCase: GetChatsUseCase = GetChatsUseCase(dbRepository = databaseChatManageRepository)
    val createChatUseCase: CreateChatUseCase = CreateChatUseCase(dbRepository = databaseChatManageRepository)
}