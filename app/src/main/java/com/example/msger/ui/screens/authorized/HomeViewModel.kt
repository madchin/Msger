package com.example.msger.ui.screens.authorized

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.data.services.auth.AuthService
import com.example.msger.data.services.db.DbService
import com.example.msger.ui.NavigationRoute
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authService: AuthService,
    private val dbService: DbService
) : ViewModel() {

    init {
        viewModelScope.launch {
            try {
                dbService.createChat()
            }
            catch(e: Throwable) {
                Log.d("HOME", e.message.toString())
            }
        }
    }

    fun signOut(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            try {
                authService.signOut()
                openAndPopUp(NavigationRoute.SignIn.route, NavigationRoute.Home.route)
            } catch (e: Throwable) {
                // TODO: catch errors properly
            }
        }
    }
}