package com.example.msger.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.msger.R
import com.example.msger.ui.components.EmailInput
import com.example.msger.ui.components.PasswordInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    navigateToSignIn: () -> Unit,
    viewModel: SignUpViewModel = viewModel(factory = SignUpViewModel.Factory)
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
        PasswordInput(
            isError = !uiState.isPasswordValid,
            value = uiState.password,
            onValueChange = viewModel::onPasswordValueChange,
            errorText = uiState.passwordErrorText
        )
        PasswordInput(
            isError = !uiState.isConfirmPasswordValid,
            value = uiState.confirmPassword,
            labelText = R.string.confirm_password,
            onValueChange = viewModel::onConfirmPasswordValueChange,
            errorText = uiState.confirmPasswordErrorText
        )
        OutlinedButton(onClick = { viewModel.signUp(openAndPopUp) }) {
            Text(text = "Sign up")
        }
        Button(onClick = navigateToSignIn) {
            Text(text = "Go to sign in")
        }
    }
    if (uiState.isLoading) {
        Text(
            text = "Loading",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        )
    }
}
