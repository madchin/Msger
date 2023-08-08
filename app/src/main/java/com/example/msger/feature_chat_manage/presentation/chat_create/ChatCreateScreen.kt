package com.example.msger.feature_chat_manage.presentation.chat_create


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.msger.R
import com.example.msger.feature_chat_manage.presentation.util.chatNameInputSupportText
import com.example.msger.feature_chat_manage.presentation.util.component.InputChatName
import com.example.msger.feature_chat_manage.presentation.util.component.InputUsername
import com.example.msger.feature_chat_manage.presentation.util.usernameInputSupportText

@Composable
fun ChatCreateScreen(
    viewModel: ChatCreateViewModel,
    openAndPopUp: (String,String) -> Unit
) {
    Column() {
        val chatNameInputSupportText: Int = chatNameInputSupportText(chatName = viewModel.chatName)
        val usernameInputSupportText: Int = usernameInputSupportText(username = viewModel.username)

        InputChatName(
            isError = !viewModel.isChatNameValid,
            value = viewModel.chatName,
            onValueChange = viewModel::onChatNameInputChange,
            onValueClear = viewModel::onChatNameValueClear,
            imeAction = ImeAction.Next,
            supportText = chatNameInputSupportText
        )
        InputUsername(
            isError = !viewModel.isUsernameValid,
            value = viewModel.username,
            onValueChange = viewModel::onUsernameInputChange,
            onValueClear = viewModel::onUsernameValueClear,
            supportText = usernameInputSupportText,
            onDonePress = { viewModel.createChat(openAndPopUp) },
        )
        OutlinedButton(onClick = { viewModel.createChat(openAndPopUp) }) {
            Text(text = stringResource(id = R.string.create_chat_button))
        }
    }
}