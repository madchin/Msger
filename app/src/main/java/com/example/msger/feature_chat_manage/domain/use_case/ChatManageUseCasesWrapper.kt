package com.example.msger.feature_chat_manage.domain.use_case

import com.example.msger.feature_chat_manage.data.repository.AuthChatManageRepositoryImpl
import com.example.msger.feature_chat_manage.domain.repository.AuthChatManageRepository
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository

class ChatManageUseCasesWrapper(
    authRepository: AuthChatManageRepository = AuthChatManageRepositoryImpl(),
    dbRepository: DatabaseChatManageRepository
) {
    val createChatUseCase = CreateChatUseCase(dbRepository = dbRepository)
    val getChatsUseCase = GetChatsUseCase(dbRepository = dbRepository)
    val joinChatUseCase = JoinChatUseCase(dbRepository = dbRepository)
    val signOutUseCase =
        SignOutUseCase(dbRepository = dbRepository, authRepository = authRepository)
    val joinChatFromChatListUseCase = JoinChatFromChatListUseCase(dbRepository = dbRepository)
}