package com.example.msger.feature_chat_manage.presentation.chat_join

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.msger.R
import com.example.msger.feature_chat_manage.presentation.util.chatIdInputSupportText
import com.example.msger.feature_chat_manage.presentation.util.component.InputChatId
import com.example.msger.feature_chat_manage.presentation.util.component.InputUsername
import com.example.msger.feature_chat_manage.presentation.util.usernameInputSupportText

@Composable
fun ChatJoinScreen(
    viewModel: ChatJoinViewModel,
    openAndPopUp: (String, String) -> Unit
) {
    val chatIdInputSupportText: Int = chatIdInputSupportText(chatId = viewModel.chatId)
    val usernameInputSupportText: Int = usernameInputSupportText(username = viewModel.username)
    val isButtonDisabled: Boolean = !viewModel.isChatIdInputValid || !viewModel.isUsernameInputValid

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputChatId(
            isError = !viewModel.isChatIdInputValid,
            value = viewModel.chatId,
            onValueChange = viewModel::onChatIdInputChange,
            onValueClear = viewModel::onChatIdValueClear,
            imeAction = ImeAction.Next,
            supportText = chatIdInputSupportText,
        )
        InputUsername(
            isError = !viewModel.isUsernameInputValid,
            value = viewModel.username,
            onValueChange = viewModel::onUsernameInputChange,
            onValueClear = viewModel::onUsernameValueClear,
            supportText = usernameInputSupportText,
            onDonePress = { viewModel.joinChat(openAndPopUp) },
        )
        OutlinedButton(
            onClick = { viewModel.joinChat(openAndPopUp) },
            enabled = !isButtonDisabled
        ) {
            Text(text = stringResource(id = R.string.join_chat_button))
        }
    }
}