package com.example.msger.ui.screens.authorized

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.example.msger.ui.components.ButtonLoader

@Composable
fun HomeScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: HomeViewModel,
) {
    val uiState by viewModel.chats.collectAsState(initial = HomeUiState.Loading)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(onClick = { viewModel.signOut(openAndPopUp) }) {
            Text(text = "sign out")
        }
        when (uiState) {
            is HomeUiState.Loading -> ButtonLoader()
            is HomeUiState.Failure -> Text(text = (uiState as HomeUiState.Failure).error?.message ?: "generic")
            is HomeUiState.Success -> (uiState as HomeUiState.Success).chats.forEach {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = it.id)
                    it.members.forEach { Text(text = it) }
                }
            }
        }
    }
}