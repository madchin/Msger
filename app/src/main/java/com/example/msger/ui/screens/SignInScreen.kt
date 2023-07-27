package com.example.msger.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.msger.ui.components.EmailInput
import com.example.msger.ui.components.PasswordInput

@Composable
fun SignInScreen(
    openAndPopUp: (String, String) -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToForgottenPassword: () -> Unit,
    viewModel: SignInViewModel,
    uiState: SignInUiState,
) {
    val isButtonEnabled =
        uiState.isEmailValid && uiState.isPasswordValid && !uiState.isLoading
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmailInput(
            isError = !uiState.isEmailValid,
            value = uiState.email,
            onValueChange = viewModel::onEmailValueChange,
            onValueClear = viewModel::onEmailValueClear,
            errorText = uiState.emailErrorText
        )
        PasswordInput(
            isError = !uiState.isPasswordValid,
            value = uiState.password,
            onValueChange = viewModel::onPasswordValueChange,
            errorText = uiState.passwordErrorText,
            onDonePress = { viewModel.signInWithEmailAndPassword(openAndPopUp) }
        )
        OutlinedButton(
            enabled = isButtonEnabled,
            onClick = { viewModel.signInWithEmailAndPassword(openAndPopUp) }
        ) {
            Text(text = "Sign in")
        }
        OutlinedButton(onClick = navigateToForgottenPassword) {
            Text(text = "Forgot password")
        }
        Button(onClick = navigateToSignUp) {
            Text(text = "Go to sign up")
        }
    }
}