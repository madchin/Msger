package com.example.msger.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.msger.common.extensions.toNavigationRoute
import com.example.msger.ui.NavigationRoute

@Composable
fun BodyLayout(
    errorMessage: String? = null,
    innerPadding: PaddingValues,
    snackbarHostState: SnackbarHostState? = null,
    route: String,
    content: @Composable (ColumnScope.() -> Unit)
) {
    val shouldSnackbarBeShown = when (route.toNavigationRoute()) {
        is NavigationRoute.SplashScreen -> false
        is NavigationRoute.Home -> false
        else -> true
    }
    Column(modifier = Modifier.padding(innerPadding)) {
        content()
        if (shouldSnackbarBeShown) {
            Snackbar(message = errorMessage!!, snackbarHostState = snackbarHostState!!)
        }
    }
}