package com.example.msger.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
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
fun SignInScreen(
    openAndPopUp: (String,String) -> Unit,
    navigateToSignUp: () -> Unit,
    viewModel: SignInViewModel = viewModel(factory = SignInViewModel.Factory)
) {
    val uiState = viewModel.uiState
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = uiState.email,
            isError = !uiState.isEmailValid,
            onValueChange = { viewModel.onInputChange(InputType.Email, it) }
        )
        TextField(
            value = uiState.password,
            isError = !uiState.isPasswordValid,
            onValueChange = { viewModel.onInputChange(InputType.Password, it) }
        )
        OutlinedButton(onClick = { viewModel.signInWithEmailAndPassword(openAndPopUp) }) {
            Text(text = "Sign in")
        }
        Button(onClick = navigateToSignUp) {
            Text(text = "Go to sign up")
        }
    }
}