package com.example.msger.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BodyLayout(
    shouldShowSnackbar: Boolean,
    modifier: Modifier = Modifier,
    errorMessage: String = "generic error placeholder",
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (ColumnScope.() -> Unit)
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(24.dp)
    ) {
        content()
        if (shouldShowSnackbar) {
            Snackbar(message = errorMessage, snackbarHostState = snackbarHostState)
        }
    }
}