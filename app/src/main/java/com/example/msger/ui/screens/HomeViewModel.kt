package com.example.msger.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.msger.MsgerApplication
import com.example.msger.data.services.AuthService
import com.example.msger.ui.navigation.HOME
import com.example.msger.ui.navigation.SIGN_IN
import kotlinx.coroutines.launch

private const val TAG = "HOME"

class HomeViewModel(private val authService: AuthService) : ViewModel() {

    fun signOut(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            try {
                authService.signOutUser()
                openAndPopUp(SIGN_IN, HOME)
                Log.d(TAG, "$TAG: User signed out")
            } catch (e: Throwable) {
                Log.d(TAG, "$TAG: User not signed out")
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY]) as MsgerApplication
                HomeViewModel(application.appContainer.authService)
            }
        }
    }

}