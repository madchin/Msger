package com.example.msger.feature_authentication.presentation.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.msger.feature_authentication.presentation.util.component.InputEmail
import com.example.msger.feature_authentication.presentation.util.component.InputPassword
import com.example.msger.feature_authentication.presentation.util.emailInputSupportText
import com.example.msger.feature_authentication.presentation.util.passwordInputSupportText
import com.example.msger.core.presentation.component.ButtonLoader

@Composable
fun SignInScreen(
    openAndPopUp: (String, String) -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToForgottenPassword: () -> Unit,
    viewModel: SignInViewModel
) {
    val isButtonEnabled =
        viewModel.isEmailValid && viewModel.isPasswordValid && !viewModel.isLoading
    val emailInputSupportText = emailInputSupportText(email = viewModel.email)
    val passwordInputSupportText = passwordInputSupportText(password = viewModel.password)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        InputEmail(
            isError = !viewModel.isEmailValid,
            value = viewModel.email,
            onValueChange = viewModel::onEmailValueChange,
            onValueClear = viewModel::onEmailValueClear,
            supportText = emailInputSupportText
        )
        InputPassword(
            isError = !viewModel.isPasswordValid,
            value = viewModel.password,
            onValueChange = viewModel::onPasswordValueChange,
            supportText = passwordInputSupportText,
            onDonePress = { viewModel.signInUser(openAndPopUp) }
        )
        OutlinedButton(
            enabled = isButtonEnabled,
            onClick = { viewModel.signInUser(openAndPopUp) }
        ) {
            if (!viewModel.isLoading) {
                Text(text = "Sign in")
            } else {
                ButtonLoader()
            }
        }
        OutlinedButton(onClick = navigateToForgottenPassword) {
            Text(text = "Forgot password")
        }
        Button(onClick = navigateToSignUp) {
            Text(text = "Go to sign up")
        }
    }
}