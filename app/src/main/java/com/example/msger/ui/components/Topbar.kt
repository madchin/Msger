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
import com.example.msger.ui.NavigationRoute

fun getTopbarTitle(route: String?): String = when (route) {
    NavigationRoute.Home.route -> "Home"
    NavigationRoute.ForgotPassword.route -> "Forgot Password"
    NavigationRoute.SignIn.route -> "Sign in"
    NavigationRoute.SignUp.route -> "Sign up"
    NavigationRoute.SplashScreen.route -> "Splash Screen"
    else -> "App bar"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MsgerTopbar(
    navController: NavHostController,
    onUpButtonClick: () -> Unit,
) {
    val navState by navController.currentBackStackEntryAsState()
    val actualRoute = navState?.destination?.route
    val topbarTitle = getTopbarTitle(actualRoute)
    val isUpButtonVisible = when (actualRoute) {
        NavigationRoute.SignIn.route -> false
        NavigationRoute.Home.route -> false
        else -> true
    }

    if (actualRoute != NavigationRoute.SplashScreen.route) {
        TopAppBar(
            title = { Text(text = topbarTitle) },
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