package com.example.msger.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.msger.common.extensions.openAndPopUp
import com.example.msger.ui.components.MsgerTopbar
import com.example.msger.ui.components.Snackbar
import com.example.msger.ui.screens.ForgotPasswordScreen
import com.example.msger.ui.screens.ForgotPasswordUiState
import com.example.msger.ui.screens.ForgotPasswordViewModel
import com.example.msger.ui.screens.SignInScreen
import com.example.msger.ui.screens.SignInUiState
import com.example.msger.ui.screens.SignInViewModel
import com.example.msger.ui.screens.SignUpScreen
import com.example.msger.ui.screens.SignUpViewModel
import com.example.msger.ui.screens.SplashScreen
import com.example.msger.ui.screens.SplashScreenViewModel
import com.example.msger.ui.screens.authorized.HomeScreen
import com.example.msger.ui.screens.authorized.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MsgerApp(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            MsgerTopbar(
                navController = navController,
                onUpButtonClick = { navController.navigateUp() }
            )
        },
        bottomBar = {},
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoute.SplashScreen.route
        ) {

            composable(NavigationRoute.SplashScreen.route) {
                val viewModel: SplashScreenViewModel = viewModel(
                    factory = ViewModelFactoryProvider.Factory
                )

                Column(modifier = Modifier.padding(innerPadding)) {
                    SplashScreen(
                        openAndPopUp = navController::openAndPopUp,
                        viewModel = viewModel
                    )
                }
            }

            composable(NavigationRoute.SignUp.route) {
                val viewModel: SignUpViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)
                val uiState = viewModel.uiState

                Column(modifier = Modifier.padding(innerPadding)) {
                    SignUpScreen(
                        openAndPopUp = navController::openAndPopUp,
                        viewModel = viewModel,
                        uiState = uiState,
                        navigateToSignIn = { navController.navigate(NavigationRoute.SignIn.route) },
                    )
                    Snackbar(message = uiState.responseError, snackbarHostState = snackbarHostState)
                }
            }

            composable(NavigationRoute.SignIn.route) {
                val viewModel: SignInViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)
                val uiState: SignInUiState = viewModel.uiState

                Column(modifier = Modifier.padding(innerPadding)) {
                    SignInScreen(
                        viewModel = viewModel,
                        uiState = uiState,
                        openAndPopUp = navController::openAndPopUp,
                        navigateToSignUp = { navController.navigate(NavigationRoute.SignUp.route) },
                        navigateToForgottenPassword = { navController.navigate(NavigationRoute.ForgotPassword.route) }
                    )
                    Snackbar(message = uiState.responseError, snackbarHostState = snackbarHostState)
                }
            }

            composable(NavigationRoute.ForgotPassword.route) {
                val viewModel: ForgotPasswordViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)
                val uiState: ForgotPasswordUiState = viewModel.uiState

                Column(modifier = Modifier.padding(innerPadding)) {
                    ForgotPasswordScreen(
                        navigateToSignIn = { navController.navigate(NavigationRoute.SignIn.route) },
                        viewModel = viewModel,
                        uiState = uiState
                    )
                    Snackbar(message = uiState.responseError, snackbarHostState = snackbarHostState)
                }
            }

            composable(NavigationRoute.Home.route) {
                Column(modifier = Modifier.padding(innerPadding)) {
                    val viewModel: HomeViewModel =
                        viewModel(factory = ViewModelFactoryProvider.Factory)

                    HomeScreen(
                        openAndPopUp = navController::openAndPopUp,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}