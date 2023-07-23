package com.example.msger.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.msger.ui.components.EmailInput

@Composable
fun ForgotPasswordScreen(
    navigateToSignIn: () -> Unit,
    viewModel: ForgotPasswordViewModel = viewModel(factory = ForgotPasswordViewModel.Factory)
) {
    val uiState = viewModel.uiState
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailInput(
            isError = !uiState.isEmailValid,
            value = uiState.email,
            onValueChange = viewModel::onEmailValueChange,
            onValueClear = viewModel::onEmailValueClear,
            errorText = uiState.emailErrorText
        )

        OutlinedButton(onClick = viewModel::resetPassword) {
            Text(text = "Reset password")
        }
        Button(onClick = navigateToSignIn) {
            Text(text = "Go to sign in")
        }
    }
}