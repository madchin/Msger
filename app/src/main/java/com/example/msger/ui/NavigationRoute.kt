package com.example.msger.ui

sealed class NavigationRoute(val route: String) {
    object Home : NavigationRoute(route = "home")
    object SignUp : NavigationRoute(route = "sign-up")
    object SignIn : NavigationRoute(route = "sign-in")
    object SplashScreen : NavigationRoute(route = "splash-screen")
    object ForgotPassword : NavigationRoute(route = "forgot-password")

    object CreateChat : NavigationRoute(route = "create-chat")

    object JoinChat : NavigationRoute(route = "join-chat")

    object Chat : NavigationRoute(route = "chat")

    object Participants : NavigationRoute(route = "participants/chat")

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