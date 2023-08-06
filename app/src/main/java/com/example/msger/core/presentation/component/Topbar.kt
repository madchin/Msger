package com.example.msger.core.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.msger.R
import com.example.msger.core.presentation.navigation.NavigationRoute
import com.example.msger.feature_authentication.presentation.util.NavigationAuthentication
import com.example.msger.feature_chat_manage.presentation.util.NavigationChatManage

fun getTopBarTitle(route: String?): String = when (route) {
    NavigationChatManage.ChatList.route -> "Home"
    NavigationAuthentication.ResetPassword.route -> "Forgot Password"
    NavigationAuthentication.SignIn.route -> "Sign in"
    NavigationAuthentication.SignUp.route -> "Sign up"
    NavigationRoute.SplashScreen.route -> "Splash Screen"
    NavigationChatManage.CreateChat.route -> "Create chat"
    NavigationRoute.Chat.withArgs("{chatId}") -> "Chat"
    NavigationRoute.Participants.withArgs("{chatId}") -> "Participants"
    else -> "App bar"
}

fun shouldUpButtonBeVisible(route: String?): Boolean = when (route) {
    NavigationAuthentication.SignIn.route -> false
    NavigationChatManage.ChatList.route -> false
    else -> true
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MsgerTopBar(
    navController: NavHostController,
    onUpButtonClick: () -> Unit,
    onPersonActionClick: (String) -> Unit,
) {
    val navState: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val actualRoute: String = navState?.destination?.route ?: ""
    val topBarTitle: String = getTopBarTitle(actualRoute)
    val isUpButtonVisible = shouldUpButtonBeVisible(actualRoute)

    if (actualRoute != NavigationRoute.SplashScreen.route) {
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
            },
            actions = {
                if (actualRoute == NavigationRoute.Chat.withArgs("{chatId}")) {
                    val chatId: String = navState?.arguments?.getString("chatId") ?: ""
                    val participantsRoute: String = NavigationRoute.Participants.withArgs(chatId)
                    IconButton(onClick = { onPersonActionClick(participantsRoute) }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Participants"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Email, contentDescription = "check")
                    }
                }
            }
        )
    }
}