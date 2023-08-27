package com.example.msger.feature_chat.presentation.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.msger.core.presentation.component.ButtonLoader
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat.domain.model.Message

@Composable
fun ChatScreen(viewModel: ChatViewModel, uiState: Resource<List<Message>>) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(uiState) {
            is Resource.Success -> {
                uiState.data?.forEach {
                    Text(text = "content is ${it.content}")
                    Text(text = "sender is ${it.sender}")
                    Text(text = "timestamp is ${it.timestamp}")
                }
            }
            is Resource.Error -> {}
            is Resource.Loading -> ButtonLoader()
        }
    }
}