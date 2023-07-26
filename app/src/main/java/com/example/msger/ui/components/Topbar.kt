package com.example.msger.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.msger.R
import com.example.msger.common.extensions.toNavigationRoute
import com.example.msger.ui.NavigationRoute

fun getTopBarTitle(route: NavigationRoute?): String = when (route) {
    is NavigationRoute.Home -> "Home"
    is NavigationRoute.ForgotPassword -> "Forgot Password"
    is NavigationRoute.SignIn -> "Sign in"
    is NavigationRoute.SignUp -> "Sign up"
    is NavigationRoute.SplashScreen -> "Splash Screen"
    else -> "App bar"
}
fun shouldUpButtonBeVisible(route: NavigationRoute?): Boolean = when(route) {
    is NavigationRoute.SignIn -> false
    is NavigationRoute.Home -> false
    else -> true
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MsgerTopBar(
    navController: NavHostController,
    onUpButtonClick: () -> Unit,
) {
    val navState by navController.currentBackStackEntryAsState()
    val actualRoute = navState?.destination?.route?.toNavigationRoute()
    val topBarTitle = getTopBarTitle(actualRoute)
    val isUpButtonVisible = shouldUpButtonBeVisible(actualRoute)

    if (actualRoute != NavigationRoute.SplashScreen) {
        TopAppBar(
            title = { Text(text = topBarTitle) },
            navigationIcon = {
                if (isUpButtonVisible) {
                    IconButton(onClick = onUpButtonClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.topbar_up_button_description)
                        )
                    }
                }
            }
        )
    }
}