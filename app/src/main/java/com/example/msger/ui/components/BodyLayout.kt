package com.example.msger.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.msger.common.extensions.toNavigationRoute
import com.example.msger.ui.NavigationRoute

@Composable
fun BodyLayout(
    route: String,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    snackbarHostState: SnackbarHostState? = null,
    content: @Composable (ColumnScope.() -> Unit)
) {
    val shouldSnackbarBeShown = when (route.toNavigationRoute()) {
        is NavigationRoute.SplashScreen -> false
        is NavigationRoute.Home -> false
        else -> true
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(24.dp)
    ) {
        content()
        if (shouldSnackbarBeShown) {
            Snackbar(message = errorMessage!!, snackbarHostState = snackbarHostState!!)
        }
    }
}