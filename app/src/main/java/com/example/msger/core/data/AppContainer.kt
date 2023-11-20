package com.example.msger.core.data

import android.content.Context
import com.example.msger.core.data.data_source.local.AppDatabase
import com.example.msger.core.util.config.RetrofitConfig
import com.example.msger.feature_authentication.domain.service.AuthService
import com.example.msger.feature_authentication.domain.use_case.AuthenticationUseCasesWrapper
import com.example.msger.feature_chat.domain.use_case.ChatUseCasesWrapper
import com.example.msger.feature_chat_manage.data.data_source.local.db.ChatDao
import com.example.msger.feature_chat_manage.data.repository.DatabaseChatManageRepositoryImpl
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository
import com.example.msger.feature_chat_manage.domain.use_case.ChatManageUseCasesWrapper
import com.example.msger.feature_onboarding.domain.use_case.OnboardingUseCasesWrapper

class AppContainer(context: Context) {
    private val localDatabaseChatManage: ChatDao =
        AppDatabase.getInstance(context = context).chatDao()
    private val databaseChatManageRepository: DatabaseChatManageRepository =
        DatabaseChatManageRepositoryImpl(localDatabase = localDatabaseChatManage)
    private val authService: AuthService = RetrofitConfig.create(AuthService::class.java)
    val chatManageUseCasesWrapper =
        ChatManageUseCasesWrapper(dbRepository = databaseChatManageRepository)

    val authenticationUseCases = AuthenticationUseCasesWrapper(authService)
    val chatUseCasesWrapper = ChatUseCasesWrapper()
    val onboardingUseCasesWrapper = OnboardingUseCasesWrapper()
}