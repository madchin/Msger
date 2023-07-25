package com.example.msger.ui.screens.authorized

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.data.services.AccountService
import com.example.msger.ui.NavigationRoute
import kotlinx.coroutines.launch

class HomeViewModel(private val accountService: AccountService) : ViewModel() {

    fun signOut(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            try {
                accountService.signOutUser()
                openAndPopUp(NavigationRoute.SignIn.route, NavigationRoute.Home.route)
            } catch (e: Throwable) {
                // TODO: catch errors properly
            }
        }
    }
}