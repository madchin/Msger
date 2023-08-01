package com.example.msger.ui.screens.authorized

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.example.msger.R
import com.example.msger.common.utils.Resource
import com.example.msger.data.model.Chat
import com.example.msger.ui.components.ButtonLoader

@Composable
fun HomeScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: HomeViewModel,
    uiState: Resource<List<Chat>>,
    navigateToCreateChat: () -> Unit,
    navigateToJoinChat: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is Resource.Loading -> ButtonLoader()
            is Resource.Error -> Text(
                text = uiState.message ?: "generic"
            )

            is Resource.Success -> {
                uiState.data?.forEach {
                    Text(text = it.name ?: "")
                }
            }
        }
        OutlinedButton(onClick = { viewModel.signOut(openAndPopUp) }) {
            Text(text = stringResource(id = R.string.sign_out_button))
        }
        OutlinedButton(onClick = navigateToCreateChat) {
            Text(text = stringResource(id = R.string.create_chat_button))
        }
        OutlinedButton(onClick = navigateToJoinChat) {
            Text(text = stringResource(id = R.string.join_chat_button))
        }
    }
}