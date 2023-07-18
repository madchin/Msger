package com.example.msger.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.msger.utils.InputType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)) {
    val uiState = viewModel.uiState
    Text(text = "Home")
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
        OutlinedButton(onClick = viewModel::signUp) {
            Text(text = "Sign up")
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
    if(uiState.isSignUpSuccess) {
        Text(
            text = "Success",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        )
    }
    if(uiState.isError) {
        Text(
            text = "ERROR",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        )
    }
}
