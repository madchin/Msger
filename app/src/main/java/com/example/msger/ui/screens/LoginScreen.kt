package com.example.msger.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.msger.utils.InputType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
) {
    val uiState = viewModel.uiState
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = uiState.email,
            onValueChange = { viewModel.onInputChange(InputType.Email, it) }
        )
        TextField(
            value = uiState.password,
            onValueChange = { viewModel.onInputChange(InputType.Password, it) }
        )
        OutlinedButton(onClick = viewModel::signInWithEmailAndPassword) {
            Text(text = "Sign in")
        }
    }
}