package com.example.msger.core.data

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
import com.example.msger.feature_chat.data.data_source.DatabaseChat
import com.example.msger.feature_chat.data.data_source.DatabaseChatImpl
import com.example.msger.feature_chat.data.repository.DatabaseChatRepositoryImpl
import com.example.msger.feature_chat.domain.repository.DatabaseChatRepository
import com.example.msger.feature_chat.domain.use_case.GetChatMembersUseCase
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
    private val databaseChatManage: DatabaseChatManage = DatabaseChatManageImpl()
    private val databaseChat: DatabaseChat = DatabaseChatImpl()

    val databaseChatManageRepository: DatabaseChatManageRepository = DatabaseChatManageRepositoryImpl(databaseChatManage)
    val databaseChatRepository: DatabaseChatRepository = DatabaseChatRepositoryImpl(dbRepository = databaseChat)
    val authRepository: AuthRepository = AuthRepositoryImpl(auth, credentialProvider)
    val resetPasswordUseCase: ResetPasswordUseCase = ResetPasswordUseCase(authRepository = authRepository)
    val signInUseCase: SignInUseCase = SignInUseCase(authRepository = authRepository)
    val getEmailFromRecoverPasswordRedirectionUseCase: GetEmailFromRecoverPasswordRedirectionUseCase =
        GetEmailFromRecoverPasswordRedirectionUseCase(authRepository = authRepository)
    val signUpUseCase: SignUpUseCase = SignUpUseCase(authRepository = authRepository)
    val signOutUseCase: SignOutUseCase = SignOutUseCase(authRepository = authRepository)
    val getChatsUseCase: GetChatsUseCase = GetChatsUseCase(dbRepository = databaseChatManageRepository)
    val createChatUseCase: CreateChatUseCase = CreateChatUseCase(dbRepository = databaseChatManageRepository)
    val getChatMembersUseCase: GetChatMembersUseCase = GetChatMembersUseCase(dbRepository = databaseChatRepository)
}