package com.example.msger.core.presentation.screens.authorized


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.msger.R
import com.example.msger.core.presentation.component.ChatNameInput
import com.example.msger.core.presentation.component.UsernameInput

@Composable
fun CreateChatScreen(
    viewModel: CreateChatViewModel,
    uiState: CreateChatUiState,
    openAndPopUp: (String,String) -> Unit
) {
    Column() {
        ChatNameInput(
            isError = !uiState.isChatNameValid,
            value = uiState.chatName,
            onValueChange = viewModel::onChatNameInputChange,
            onValueClear = viewModel::onChatNameValueClear,
            imeAction = ImeAction.Next,
            supportText = uiState.chatNameInputErrorText
        )
        UsernameInput(
            isError = !uiState.isUsernameValid,
            value = uiState.username,
            onValueChange = viewModel::onUsernameInputChange,
            onValueClear = viewModel::onUsernameValueClear,
            supportText = uiState.usernameInputErrorText,
            onDonePress = { viewModel.createChat(openAndPopUp) },
        )
        OutlinedButton(onClick = { viewModel.createChat(openAndPopUp) }) {
            Text(text = stringResource(id = R.string.create_chat_button))
        }
    }
}