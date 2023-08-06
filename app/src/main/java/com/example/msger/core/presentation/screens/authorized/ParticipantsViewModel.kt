package com.example.msger.core.presentation.screens.authorized

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.core.util.Resource
import com.example.msger.core.data.model.Member
import com.example.msger.data.services.db.DbService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ParticipantsUiState(val participants: List<Member> = listOf())
class ParticipantsViewModel(savedStateHandle: SavedStateHandle, dbService: DbService) :
    ViewModel() {

    private val chatId: String = checkNotNull(savedStateHandle["chatId"])

    private val _uiState: StateFlow<Resource<ParticipantsUiState>> = dbService
        .getChatMembers(chatId)
        .map {
            when {
                it.isSuccess -> {
                    val chatMembers = it.getOrDefault(listOf())
                        .filterNotNull()
                        .map { members ->
                            members.values.sortedBy { member ->
                                member.lastSeen
                            }
                        }
                    Resource.Success(ParticipantsUiState(chatMembers.flatten()))
                }

                it.isFailure -> {
                    val error = it.exceptionOrNull()
                    Resource.Error(error?.message.toString())
                }

                else -> Resource.Loading()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Resource.Loading()
        )
    val uiState: StateFlow<Resource<ParticipantsUiState>>
        get() = _uiState

}