package com.example.msger.ui.screens.authorized

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.data.services.AccountService
import com.example.msger.ui.HOME
import com.example.msger.ui.HOME_DEBUG_TAG
import com.example.msger.ui.SIGN_IN
import kotlinx.coroutines.launch

class HomeViewModel(private val accountService: AccountService) : ViewModel() {

    fun signOut(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            try {
                accountService.signOutUser()
                openAndPopUp(SIGN_IN, HOME)
                Log.d(HOME_DEBUG_TAG, "$HOME_DEBUG_TAG: User signed out")
            } catch (e: Throwable) {
                Log.d(HOME_DEBUG_TAG, "$HOME_DEBUG_TAG: User not signed out")
            }
        }
    }
}