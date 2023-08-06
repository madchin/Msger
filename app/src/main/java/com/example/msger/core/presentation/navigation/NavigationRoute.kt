package com.example.msger.core.presentation.navigation

sealed class NavigationRoute(val route: String) {
    object SplashScreen : NavigationRoute(route = "splash-screen")

    object SignIn : NavigationRoute(route = "sign_in")

    object SignUp : NavigationRoute(route = "sign_up")

    object ResetPassword : NavigationRoute(route = "reset-password")

    object Chat : NavigationRoute(route = "chat")

    object Participants : NavigationRoute(route = "participants/chat")

    object ChatList : NavigationRoute(route = "home")

    object CreateChat : NavigationRoute(route = "create-chat")

    object JoinChat : NavigationRoute(route = "join-chat")

    fun withArgs(vararg args: String?): String {
        return buildString {
            append(route)
            args.forEach { arg -> append("/$arg") }
        }
    }

    fun withQueryParams(vararg args: Map<String, String>?): String {
        return buildString {
            append(route)
            args.forEachIndexed { index, arg ->
                append(if (index == 0) "?" else "&")
                arg?.forEach { (query, value) ->
                    append("$query=$value")
                }
            }
        }
    }
}