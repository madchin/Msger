package com.example.msger.feature_chat.presentation.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.msger.R
import com.example.msger.core.presentation.component.ButtonLoader
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat.domain.model.Message
import com.example.msger.feature_chat.presentation.util.component.ChatMessage
import com.example.msger.feature_chat.presentation.util.component.InputChatMessage

@Composable
fun ChatScreen(viewModel: ChatViewModel, uiState: Resource<List<Message>>) {
    val supportText: Int =
        if (viewModel.isInputValid) R.string.send_chat_message else R.string.input_required
    Column(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is Resource.Success -> {
                LazyColumn(modifier = Modifier.weight(4f)) {
                    items(uiState.data ?: listOf(), key = { it.content + it.timestamp }) {
                        ChatMessage(message = it)
                    }

                }
                Column(modifier = Modifier.weight(1f)) {
                    InputChatMessage(
                        isError = !viewModel.isInputValid,
                        value = viewModel.inputValue,
                        onDonePress = viewModel::sendMessage,
                        onValueChange = viewModel::onInputChange,
                        supportText = supportText,
                    )
                }
            }

            is Resource.Error -> {}
            is Resource.Loading -> ButtonLoader()
        }
    }
}
