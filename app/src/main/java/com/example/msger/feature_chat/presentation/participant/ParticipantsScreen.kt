package com.example.msger.feature_chat.presentation.participant

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.msger.core.domain.model.Member
import com.example.msger.core.util.Resource

@Composable
fun ParticipantsScreen(
    uiState: Resource<List<Member>>
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d("TAG", "data is ${uiState.data}")
        uiState.data?.forEach {
            Text(text = it.name)
            Text(text = it.lastSeen.toString())
        }
    }
}