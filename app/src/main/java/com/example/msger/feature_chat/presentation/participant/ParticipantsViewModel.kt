package com.example.msger.feature_chat.presentation.participant

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.core.util.Resource
import com.example.msger.core.domain.model.Member
import com.example.msger.feature_chat.domain.use_case.GetChatMembersUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ParticipantsViewModel(
    savedStateHandle: SavedStateHandle,
    getChatMembersUseCase: GetChatMembersUseCase
) :
    ViewModel() {

    private val chatId: String = checkNotNull(savedStateHandle["chatId"])

    private val _uiState: StateFlow<Resource<List<Member>>> = getChatMembersUseCase(chatId = chatId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Resource.Loading()
        )
    val uiState: StateFlow<Resource<List<Member>>>
        get() = _uiState

}