package com.example.msger.ui.screens.authorized

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.msger.common.utils.Resource

@Composable
fun ParticipantsScreen(
    uiState: Resource<ParticipantsUiState>
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        uiState.data?.participants?.forEach {
            Text(text = it.name ?: "")
            Text(text = it.lastSeen.toString())
        }
    }
}