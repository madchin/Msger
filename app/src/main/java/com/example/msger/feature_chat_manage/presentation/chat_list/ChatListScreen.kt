package com.example.msger.feature_chat_manage.presentation.chat_list

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.msger.R
import com.example.msger.core.presentation.component.ButtonLoader
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat_manage.presentation.chat_list.component.ChatListItem
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    navigateToChat: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: ChatListViewModel,
    uiState: Resource<List<Chat>>,
    navigateToCreateChat: () -> Unit,
    navigateToJoinChat: () -> Unit
) {
    LazyColumn {
        when (uiState) {
            is Resource.Loading -> item { ButtonLoader() }
            is Resource.Error -> {}
            is Resource.Success -> {
                if (uiState.data?.isEmpty() == true) {
                    item { Text(text = "Chats not visible") }
                } else {
                    items(uiState.data ?: listOf()) {
                        val date: String =
                            SimpleDateFormat(
                                "MM/dd/yyyy HH:mm:ss",
                                Locale.getDefault()
                            ).format(Date((it.lastSeen ?: 0) * 1000))
                        val supportingText =
                            stringResource(id = R.string.chat_list_supporting_text_prefix) + " $date"
                        val headlineText: String = it.name
                        ChatListItem(
                            headlineText = headlineText,
                            supportingText = supportingText,
                            onItemClick = {
                                viewModel.joinChat(chatId = it.id ?: "", navigateToChat)

                            }
                        )
                    }
                }
                item {
                    OutlinedButton(onClick = { viewModel.signOut(openAndPopUp) }) {
                        Text(text = stringResource(id = R.string.sign_out_button))
                    }
                }
                item {
                    OutlinedButton(onClick = navigateToCreateChat) {
                        Text(text = stringResource(id = R.string.create_chat_button))
                    }
                }
                item {
                    OutlinedButton(onClick = navigateToJoinChat) {
                        Text(text = stringResource(id = R.string.join_chat_button))
                    }
                }
            }
        }
    }

}