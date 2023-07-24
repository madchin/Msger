package com.example.msger.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.msger.ui.components.EmailInput
import com.example.msger.ui.components.PasswordInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    openAndPopUp: (String, String) -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToForgottenPassword: () -> Unit,
    viewModel: SignInViewModel = viewModel(factory = SignInViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val responseError = uiState.responseError

    Scaffold(
        topBar = {},
        bottomBar = {},
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
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
            OutlinedButton(onClick = { viewModel.signInWithEmailAndPassword(openAndPopUp) }) {
                Text(text = "Sign in")
            }
            OutlinedButton(onClick = navigateToForgottenPassword) {
                Text(text = "Forgot password")
            }
            Button(onClick = navigateToSignUp) {
                Text(text = "Go to sign up")
            }
        }
        if (responseError.isNotEmpty()) {
            LaunchedEffect(responseError) {
                snackbarHostState.showSnackbar(
                    message = responseError,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

}