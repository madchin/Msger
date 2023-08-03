package com.example.msger.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.msger.common.extensions.openAndPopUp
import com.example.msger.common.utils.Resource
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
import com.example.msger.ui.screens.authorized.ChatScreen
import com.example.msger.ui.screens.authorized.ChatViewModel
import com.example.msger.ui.screens.authorized.CreateChatScreen
import com.example.msger.ui.screens.authorized.CreateChatUiState
import com.example.msger.ui.screens.authorized.CreateChatViewModel
import com.example.msger.ui.screens.authorized.HomeScreen
import com.example.msger.ui.screens.authorized.HomeUiState
import com.example.msger.ui.screens.authorized.HomeViewModel
import com.example.msger.ui.screens.authorized.ParticipantsScreen
import com.example.msger.ui.screens.authorized.ParticipantsUiState
import com.example.msger.ui.screens.authorized.ParticipantsViewModel

fun shouldSnackbarBeShown(route: String?) = when (route) {
    NavigationRoute.SplashScreen.route -> false
    else -> true
}

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
                onUpButtonClick = { navController.navigateUp() },
                onPersonActionClick = { navController.navigate(NavigationRoute.Participants.route + "/{chatId}") }
            )
        },
        bottomBar = {},
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = NavigationRoute.SplashScreen.route,
            modifier = Modifier.fillMaxSize()
        ) {
            val bodyLayoutModifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            composable(route = NavigationRoute.SplashScreen.route) {
                val viewModel: SplashScreenViewModel = viewModel(
                    factory = ViewModelFactoryProvider.Factory
                )

                BodyLayout(
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    modifier = bodyLayoutModifier
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
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    snackbarHostState = snackbarHostState,
                    errorMessage = uiState.responseError,
                    modifier = bodyLayoutModifier
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
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    snackbarHostState = snackbarHostState,
                    errorMessage = uiState.responseError,
                    modifier = bodyLayoutModifier
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
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    snackbarHostState = snackbarHostState,
                    errorMessage = uiState.responseError,
                    modifier = bodyLayoutModifier
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
                val uiState: Resource<HomeUiState> by viewModel.uiState.collectAsStateWithLifecycle()

                BodyLayout(
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    snackbarHostState = snackbarHostState,
                    errorMessage = uiState.message ?: "",
                    modifier = bodyLayoutModifier
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
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    modifier = bodyLayoutModifier
                ) {
                    CreateChatScreen(
                        viewModel = viewModel,
                        uiState = uiState,
                        openAndPopUp = navController::openAndPopUp
                    )
                }
            }
            composable(
                route = NavigationRoute.Chat.route + "/{chatId}",
                arguments = listOf(
                    navArgument("chatId") { type = NavType.StringType }
                )
            ) {
                val viewModel: ChatViewModel = viewModel(factory = ViewModelFactoryProvider.Factory)
                BodyLayout(
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    modifier = bodyLayoutModifier
                ) {
                    ChatScreen(
                        viewModel = viewModel
                    )
                }
            }
            composable(
                route = NavigationRoute.Participants.route + "/{chatId}",
                arguments = listOf(
                    navArgument("chatId") { type = NavType.StringType }
                )
            ) {
                val viewModel: ParticipantsViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)
                val uiState: Resource<ParticipantsUiState> by viewModel.uiState.collectAsStateWithLifecycle()
                BodyLayout(
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    modifier = bodyLayoutModifier
                ) {
                    ParticipantsScreen(
                        uiState = uiState
                    )
                }
            }
        }
    }
}