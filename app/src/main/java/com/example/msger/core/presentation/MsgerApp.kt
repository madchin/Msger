package com.example.msger.core.presentation

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
import com.example.msger.core.presentation.component.BodyLayout
import com.example.msger.core.presentation.component.MsgerTopBar
import com.example.msger.core.presentation.navigation.NavigationRoute
import com.example.msger.core.presentation.util.ViewModelFactoryProvider
import com.example.msger.core.util.Resource
import com.example.msger.core.util.extension.openAndPopUp
import com.example.msger.feature_authentication.presentation.reset_password.RecoverPasswordScreen
import com.example.msger.feature_authentication.presentation.reset_password.ResetPasswordViewModel
import com.example.msger.feature_authentication.presentation.sign_in.SignInScreen
import com.example.msger.feature_authentication.presentation.sign_in.SignInViewModel
import com.example.msger.feature_authentication.presentation.sign_up.SignUpScreen
import com.example.msger.feature_authentication.presentation.sign_up.SignUpViewModel
import com.example.msger.feature_chat.presentation.chat.ChatScreen
import com.example.msger.feature_chat.presentation.chat.ChatViewModel
import com.example.msger.feature_chat.presentation.participant.ParticipantsScreen
import com.example.msger.feature_chat.presentation.participant.ParticipantsViewModel
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat_manage.presentation.chat_create.ChatCreateScreen
import com.example.msger.feature_chat_manage.presentation.chat_create.ChatCreateViewModel
import com.example.msger.feature_chat_manage.presentation.chat_join.ChatJoinScreen
import com.example.msger.feature_chat_manage.presentation.chat_join.ChatJoinViewModel
import com.example.msger.feature_chat_manage.presentation.chat_list.ChatListViewModel
import com.example.msger.feature_chat_manage.presentation.chat_list.HomeScreen
import com.example.msger.feature_onboarding.presentation.SplashScreen
import com.example.msger.feature_onboarding.presentation.SplashScreenViewModel

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
                onPersonActionClick = navController::navigate
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

                BodyLayout(
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    snackbarHostState = snackbarHostState,
                    errorMessage = viewModel.responseError,
                    modifier = bodyLayoutModifier
                ) {
                    SignUpScreen(
                        openAndPopUp = navController::openAndPopUp,
                        viewModel = viewModel,
                        navigateToSignIn = { navController.navigate(NavigationRoute.SignIn.route) },
                    )
                }
            }

            composable(route = NavigationRoute.SignIn.route) {
                val viewModel: SignInViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)

                BodyLayout(
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    snackbarHostState = snackbarHostState,
                    errorMessage = viewModel.responseError,
                    modifier = bodyLayoutModifier
                ) {
                    SignInScreen(
                        viewModel = viewModel,
                        openAndPopUp = navController::openAndPopUp,
                        navigateToSignUp = { navController.navigate(NavigationRoute.SignUp.route) },
                        navigateToForgottenPassword = { navController.navigate(NavigationRoute.ResetPassword.route) }
                    )
                }
            }

            composable(route = NavigationRoute.ResetPassword.route) {
                val viewModel: ResetPasswordViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)

                BodyLayout(
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    snackbarHostState = snackbarHostState,
                    errorMessage = viewModel.responseError,
                    modifier = bodyLayoutModifier
                ) {
                    RecoverPasswordScreen(
                        navigateToSignIn = { navController.navigate(NavigationRoute.SignIn.route) },
                        viewModel = viewModel
                    )
                }
            }

            composable(route = NavigationRoute.ChatList.route) {
                val viewModel: ChatListViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)
                val uiState: Resource<List<Chat>> by viewModel.chats.collectAsStateWithLifecycle()

                BodyLayout(
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    snackbarHostState = snackbarHostState,
                    errorMessage = uiState.message ?: "",
                    modifier = bodyLayoutModifier
                ) {
                    HomeScreen(
                        navigateToChat = navController::navigate,
                        openAndPopUp = navController::openAndPopUp,
                        viewModel = viewModel,
                        uiState = uiState,
                        navigateToCreateChat = { navController.navigate(NavigationRoute.CreateChat.route) },
                        navigateToJoinChat = { navController.navigate(NavigationRoute.JoinChat.route) }
                    )
                }
            }
            composable(route = NavigationRoute.CreateChat.route) {
                val viewModel: ChatCreateViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)

                BodyLayout(
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    snackbarHostState = snackbarHostState,
                    errorMessage = viewModel.responseError,
                    modifier = bodyLayoutModifier
                ) {
                    ChatCreateScreen(
                        viewModel = viewModel,
                        openAndPopUp = navController::openAndPopUp
                    )
                }
            }
            composable(route = NavigationRoute.JoinChat.route) {
                val viewModel: ChatJoinViewModel =
                    viewModel(factory = ViewModelFactoryProvider.Factory)

                BodyLayout(
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    snackbarHostState = snackbarHostState,
                    errorMessage = viewModel.responseError,
                    modifier = bodyLayoutModifier
                ) {
                    ChatJoinScreen(
                        viewModel = viewModel,
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
                BodyLayout(
                    shouldShowSnackbar = shouldSnackbarBeShown(route),
                    modifier = bodyLayoutModifier
                ) {
                    ParticipantsScreen(
                        viewModel = viewModel
                    )
                }
            }

        }
    }
}