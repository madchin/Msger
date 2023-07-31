package com.example.msger.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import com.example.msger.R
import com.example.msger.ui.components.ButtonLoader
import com.example.msger.ui.components.EmailInput
import com.example.msger.ui.components.PasswordInput

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    navigateToSignIn: () -> Unit,
    viewModel: SignUpViewModel,
    uiState: SignUpUiState
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val isButtonEnabled =
            uiState.isEmailValid && uiState.isPasswordValid && uiState.isConfirmPasswordValid && !uiState.isLoading
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
            imeAction = ImeAction.Next
        )
        PasswordInput(
            isError = !uiState.isConfirmPasswordValid,
            value = uiState.confirmPassword,
            labelText = R.string.confirm_password,
            onValueChange = viewModel::onConfirmPasswordValueChange,
            errorText = uiState.confirmPasswordErrorText,
            onDonePress = { viewModel.signUpUser(openAndPopUp) }
        )
        OutlinedButton(
            enabled = isButtonEnabled,
            onClick = { viewModel.signUpUser(openAndPopUp) }
        ) {
            if (!uiState.isLoading) {
                Text(text = "Sign up")
            } else {
                ButtonLoader()
            }
        }
        Button(onClick = navigateToSignIn) {
            Text(text = "Go to sign in")
        }
    }
}
