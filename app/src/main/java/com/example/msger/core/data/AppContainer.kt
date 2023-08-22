package com.example.msger.core.data

import android.content.Context
import com.example.msger.core.data.data_source.local.AppDatabase
import com.example.msger.feature_authentication.data.data_source.Auth
import com.example.msger.feature_authentication.data.data_source.AuthImpl
import com.example.msger.feature_authentication.data.data_source.DeepLinkHandler
import com.example.msger.feature_authentication.data.data_source.DeepLinkHandlerImpl
import com.example.msger.feature_authentication.data.repository.AuthRepositoryImpl
import com.example.msger.feature_authentication.domain.repository.AuthRepository
import com.example.msger.feature_authentication.domain.use_case.GetEmailFromDeepLinkUseCase
import com.example.msger.feature_authentication.domain.use_case.ResetPasswordUseCase
import com.example.msger.feature_authentication.domain.use_case.SignInUseCase
import com.example.msger.feature_authentication.domain.use_case.SignUpUseCase
import com.example.msger.feature_chat.data.data_source.db.RemoteDatabaseChat
import com.example.msger.feature_chat.data.data_source.db.RemoteDatabaseChatImpl
import com.example.msger.feature_chat.data.repository.DatabaseChatRepositoryImpl
import com.example.msger.feature_chat.domain.repository.DatabaseChatRepository
import com.example.msger.feature_chat.domain.use_case.GetChatMembersUseCase
import com.example.msger.feature_chat_manage.data.data_source.local.db.ChatDao
import com.example.msger.feature_chat_manage.data.data_source.remote.auth.AuthChatManage
import com.example.msger.feature_chat_manage.data.data_source.remote.auth.AuthChatManageImpl
import com.example.msger.feature_chat_manage.data.data_source.remote.db.RemoteDatabaseChatManage
import com.example.msger.feature_chat_manage.data.data_source.remote.db.RemoteDatabaseChatManageImpl
import com.example.msger.feature_chat_manage.data.repository.AuthChatManageRepositoryImpl
import com.example.msger.feature_chat_manage.data.repository.DatabaseChatManageRepositoryImpl
import com.example.msger.feature_chat_manage.domain.repository.AuthChatManageRepository
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository
import com.example.msger.feature_chat_manage.domain.use_case.CreateChatUseCase
import com.example.msger.feature_chat_manage.domain.use_case.GetChatsUseCase
import com.example.msger.feature_chat_manage.domain.use_case.JoinChatUseCase
import com.example.msger.feature_chat_manage.domain.use_case.SignOutUseCase
import com.example.msger.feature_onboarding.data.data_source.AuthenticatorOnboarding
import com.example.msger.feature_onboarding.data.data_source.AuthenticatorOnboardingImpl
import com.example.msger.feature_onboarding.data.repository.AuthOnboardingRepositoryImpl
import com.example.msger.feature_onboarding.domain.repository.AuthOnboardingRepository
import com.example.msger.feature_onboarding.domain.use_case.IsUserSignedInUseCase

class AppContainer(context: Context) {
    private val auth: Auth = AuthImpl()
    private val deepLinkHandler: DeepLinkHandler = DeepLinkHandlerImpl()
    private val localDatabaseChatManage: ChatDao =
        AppDatabase.getInstance(context = context).chatDao()
    private val remoteDatabaseChatManage: RemoteDatabaseChatManage = RemoteDatabaseChatManageImpl()
    private val remoteDatabaseChat: RemoteDatabaseChat = RemoteDatabaseChatImpl()
    private val authOnboarding: AuthenticatorOnboarding = AuthenticatorOnboardingImpl()
    private val authOnboardingRepository: AuthOnboardingRepository =
        AuthOnboardingRepositoryImpl(auth = authOnboarding)
    private val databaseChatManageRepository: DatabaseChatManageRepository =
        DatabaseChatManageRepositoryImpl(
            remoteDatabase = remoteDatabaseChatManage,
            localDatabase = localDatabaseChatManage
        )
    private val databaseChatRepository: DatabaseChatRepository =
        DatabaseChatRepositoryImpl(dbRepository = remoteDatabaseChat)
    private val authRepository: AuthRepository = AuthRepositoryImpl(auth, deepLinkHandler)

    private val authChatManage: AuthChatManage = AuthChatManageImpl()
    private val authChatManageRepository: AuthChatManageRepository =
        AuthChatManageRepositoryImpl(authChatManage = authChatManage)

    val resetPasswordUseCase: ResetPasswordUseCase =
        ResetPasswordUseCase(authRepository = authRepository)
    val signInUseCase: SignInUseCase = SignInUseCase(authRepository = authRepository)
    val getEmailFromDeepLinkUseCase: GetEmailFromDeepLinkUseCase =
        GetEmailFromDeepLinkUseCase(authRepository = authRepository)
    val signUpUseCase: SignUpUseCase = SignUpUseCase(authRepository = authRepository)
    val signOutUseCase: SignOutUseCase = SignOutUseCase(
        authChatManageRepository = authChatManageRepository,
        dbRepository = databaseChatManageRepository
    )
    val getChatsUseCase: GetChatsUseCase =
        GetChatsUseCase(dbRepository = databaseChatManageRepository)
    val createChatUseCase: CreateChatUseCase =
        CreateChatUseCase(dbRepository = databaseChatManageRepository)
    val getChatMembersUseCase: GetChatMembersUseCase =
        GetChatMembersUseCase(dbRepository = databaseChatRepository)
    val isUserSignedInUseCase: IsUserSignedInUseCase =
        IsUserSignedInUseCase(authOnboardingRepository = authOnboardingRepository)
    val joinChatUseCase: JoinChatUseCase =
        JoinChatUseCase(dbRepository = databaseChatManageRepository)


}