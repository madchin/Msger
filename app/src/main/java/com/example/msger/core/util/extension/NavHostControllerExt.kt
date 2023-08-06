package com.example.msger.core.util.extension

import androidx.navigation.NavHostController

fun NavHostController.openAndPopUp(destination: String, popUpDestination: String) {
    navigate(route = destination) {
        popBackStack(route = popUpDestination, inclusive = true)
    }
}