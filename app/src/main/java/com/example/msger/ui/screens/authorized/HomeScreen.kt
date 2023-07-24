package com.example.msger.ui.screens.authorized

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun HomeScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: HomeViewModel,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "home")
        OutlinedButton(onClick = { viewModel.signOut(openAndPopUp) }) {
            Text(text = "sign out")
        }
    }
}