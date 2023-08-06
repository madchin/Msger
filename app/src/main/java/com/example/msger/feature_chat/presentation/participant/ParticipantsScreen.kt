package com.example.msger.feature_chat.presentation.participant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat.domain.model.Member

@Composable
fun ParticipantsScreen(
    viewModel: ParticipantsViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val uiState: Resource<List<Member>> by viewModel.uiState.collectAsStateWithLifecycle()
        uiState.data?.forEach {
            Text(text = it.name ?: "")
            Text(text = it.lastSeen.toString())
        }
    }
}