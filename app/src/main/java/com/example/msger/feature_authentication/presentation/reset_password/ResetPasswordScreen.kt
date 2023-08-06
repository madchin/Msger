package com.example.msger.feature_authentication.presentation.reset_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import com.example.msger.feature_authentication.presentation.util.component.InputEmail
import com.example.msger.feature_authentication.presentation.util.emailInputSupportText
import com.example.msger.core.presentation.component.ButtonLoader

@Composable
fun RecoverPasswordScreen(
    navigateToSignIn: () -> Unit,
    viewModel: ResetPasswordViewModel
) {
    val isButtonEnabled = viewModel.isEmailValid && !viewModel.isLoading
    val emailInputSupportText = emailInputSupportText(email = viewModel.email)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputEmail(
            isError = !viewModel.isEmailValid,
            value = viewModel.email,
            onValueChange = viewModel::onEmailValueChange,
            onValueClear = viewModel::onEmailValueClear,
            supportText = emailInputSupportText,
            imeAction = ImeAction.Done,
            onDonePress = viewModel::resetPassword
        )

        OutlinedButton(
            enabled = isButtonEnabled,
            onClick = viewModel::resetPassword
        ) {
            if (!viewModel.isLoading) {
                Text(text = "Reset password")
            } else {
                ButtonLoader()
            }
        }
        Button(onClick = navigateToSignIn) {
            Text(text = "Go to sign in")
        }
    }
}