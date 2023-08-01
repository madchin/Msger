package com.example.msger.ui.screens.authorized


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.msger.R
import com.example.msger.ui.components.ChatNameInput
import com.example.msger.ui.components.UsernameInput

@Composable
fun CreateChatScreen(
    viewModel: CreateChatViewModel,
    uiState: CreateChatUiState
) {
    Column() {
        ChatNameInput(
            isError = !uiState.isChatNameValid,
            value = uiState.chatName,
            onValueChange = viewModel::onChatNameInputChange,
            onValueClear = viewModel::onChatNameValueClear,
            imeAction = ImeAction.Next,
            errorText = uiState.chatNameInputErrorText
        )
        UsernameInput(
            isError = !uiState.isUsernameValid,
            value = uiState.username,
            onValueChange = viewModel::onUsernameInputChange,
            onValueClear = viewModel::onUsernameValueClear,
            errorText = uiState.usernameInputErrorText,
            onDonePress = viewModel::createChat,
        )
        OutlinedButton(onClick = viewModel::createChat) {
            Text(text = stringResource(id = R.string.create_chat_button))
        }
    }
}