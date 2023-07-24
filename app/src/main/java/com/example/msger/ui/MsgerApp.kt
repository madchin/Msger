package com.example.msger.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.msger.common.extensions.openAndPopUp
import com.example.msger.ui.screens.ForgotPasswordScreen
import com.example.msger.ui.screens.SignInScreen
import com.example.msger.ui.screens.SignInUiState
import com.example.msger.ui.screens.SignInViewModel
import com.example.msger.ui.screens.SignUpScreen
import com.example.msger.ui.screens.SplashScreen
import com.example.msger.ui.screens.authorized.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MsgerApp(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {},
        bottomBar = {},
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
        NavHost(navController = navController, startDestination = SPLASH_SCREEN) {
            composable(SPLASH_SCREEN) {
                SplashScreen(openAndPopUp = navController::openAndPopUp)
            }
            composable(SIGN_UP) {
                SignUpScreen(
                    openAndPopUp = navController::openAndPopUp,
                    navigateToSignIn = { navController.navigate(SIGN_IN) },
                )
            }
            composable(SIGN_IN) {
                val viewModel: SignInViewModel = viewModel(factory = SignInViewModel.Factory)
                val uiState: SignInUiState = viewModel.uiState
                val responseError = uiState.responseError

                SignInScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    innerPadding = innerPadding,
                    openAndPopUp = navController::openAndPopUp,
                    navigateToSignUp = { navController.navigate(SIGN_UP) },
                    navigateToForgottenPassword = { navController.navigate(FORGOT_PASSWORD) }
                )
                if (responseError.isNotEmpty()) {
                    LaunchedEffect(responseError) {
                        snackbarHostState.showSnackbar(
                            message = responseError,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
            composable(FORGOT_PASSWORD) {
                ForgotPasswordScreen(
                    navigateToSignIn = { navController.navigate(SIGN_IN) }
                )
            }
            composable(HOME) {
                HomeScreen(openAndPopUp = navController::openAndPopUp)
            }
        }
    }
}