package com.example.msger.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.msger.common.extensions.openAndPopUp
import com.example.msger.common.utils.Resource
import com.example.msger.data.model.db.ChatEntity
import com.example.msger.ui.components.BodyLayout
import com.example.msger.ui.components.MsgerTopBar
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
import com.example.msger.ui.screens.authorized.CreateChatScreen
import com.example.msger.ui.screens.authorized.CreateChatUiState
import com.example.msger.ui.screens.authorized.CreateChatViewModel
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
            MsgerTopBar(
                navController = navController,
                onUpButtonClick = { navController.navigateUp() }
            )
        },
        bottomBar = {},
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = NavigationRoute.SplashScreen.route,
            modifier = Modifier.fillMaxSize()
        ) {

            composable(route = NavigationRoute.SplashScreen.route) {
                val viewModel: SplashScreenViewModel = viewModel(
                    factory = ViewModelFactoryProvider.Factory
                )

                BodyLayout(
                    route = NavigationRoute.SplashScreen.route,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    SplashScreen(
                        openAndPopUp = navController::openAndPopUp,
                        viewModel = viewModel,
                    )
                }
            }

            composable(route = NavigationRoute.SignUp.route) {
                val viewModel: SignUpViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)
                val uiState = viewModel.uiState

                BodyLayout(
                    route = NavigationRoute.SignUp.route,
                    snackbarHostState = snackbarHostState,
                    errorMessage = uiState.responseError,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    SignUpScreen(
                        openAndPopUp = navController::openAndPopUp,
                        viewModel = viewModel,
                        uiState = uiState,
                        navigateToSignIn = { navController.navigate(NavigationRoute.SignIn.route) },
                    )
                }
            }

            composable(route = NavigationRoute.SignIn.route) {
                val viewModel: SignInViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)
                val uiState: SignInUiState = viewModel.uiState

                BodyLayout(
                    route = NavigationRoute.SignIn.route,
                    snackbarHostState = snackbarHostState,
                    errorMessage = uiState.responseError,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    SignInScreen(
                        viewModel = viewModel,
                        uiState = uiState,
                        openAndPopUp = navController::openAndPopUp,
                        navigateToSignUp = { navController.navigate(NavigationRoute.SignUp.route) },
                        navigateToForgottenPassword = { navController.navigate(NavigationRoute.ForgotPassword.route) }
                    )
                }
            }

            composable(route = NavigationRoute.ForgotPassword.route) {
                val viewModel: ForgotPasswordViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)
                val uiState: ForgotPasswordUiState = viewModel.uiState

                BodyLayout(
                    route = NavigationRoute.ForgotPassword.route,
                    snackbarHostState = snackbarHostState,
                    errorMessage = uiState.responseError,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    ForgotPasswordScreen(
                        navigateToSignIn = { navController.navigate(NavigationRoute.SignIn.route) },
                        viewModel = viewModel,
                        uiState = uiState
                    )
                }
            }

            composable(route = NavigationRoute.Home.route) {
                val viewModel: HomeViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)
                val uiState: Resource<List<ChatEntity>> by viewModel.chats.collectAsState()

                BodyLayout(
                    route = NavigationRoute.Home.route,
                    snackbarHostState = snackbarHostState,
                    errorMessage = uiState.message ?: "",
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    HomeScreen(
                        openAndPopUp = navController::openAndPopUp,
                        viewModel = viewModel,
                        uiState = uiState,
                        navigateToCreateChat = { navController.navigate(NavigationRoute.CreateChat.route) },
                        navigateToJoinChat = { navController.navigate(NavigationRoute.JoinChat.route) }
                    )
                }
            }
            composable(route = NavigationRoute.CreateChat.route) {
                val viewModel: CreateChatViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)
                val uiState: CreateChatUiState = viewModel.uiState
                BodyLayout(
                    route = NavigationRoute.CreateChat.route,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    CreateChatScreen(viewModel = viewModel, uiState = uiState)
                }
            }
        }
    }
}