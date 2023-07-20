package com.example.msger.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.msger.utils.InputType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    navigateToSignIn: () -> Unit,
    viewModel: SignUpViewModel = viewModel(factory = SignUpViewModel.Factory)
) {
    val uiState = viewModel.uiState
    Text(text = "Sign up")
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = uiState.email,
            isError = !uiState.isEmailValid,
            onValueChange = { viewModel.onInputChange(InputType.Email, it) }
        )
        Spacer(modifier = Modifier.size(4.dp))
        TextField(
            value = uiState.password,
            isError = !uiState.isPasswordValid,
            onValueChange = { viewModel.onInputChange(InputType.Password, it) }
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
