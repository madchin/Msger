package com.example.msger.feature_authentication.presentation.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import com.example.msger.R
import com.example.msger.feature_authentication.presentation.util.component.InputEmail
import com.example.msger.feature_authentication.presentation.util.component.InputPassword
import com.example.msger.feature_authentication.presentation.util.confirmPasswordInputSupportText
import com.example.msger.feature_authentication.presentation.util.emailInputSupportText
import com.example.msger.feature_authentication.presentation.util.passwordInputSupportText
import com.example.msger.core.presentation.component.ButtonLoader

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    navigateToSignIn: () -> Unit,
    viewModel: SignUpViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val isButtonEnabled =
            viewModel.isEmailValid && viewModel.isPasswordValid && viewModel.isConfirmPasswordValid && !viewModel.isLoading
        val emailInputSupportText: Int = emailInputSupportText(email = viewModel.email)
        val passwordInputSupportText: Int = passwordInputSupportText(password = viewModel.password)
        val confirmPasswordInputSupportText: Int = confirmPasswordInputSupportText(
            password = viewModel.password,
            confirmPassword = viewModel.confirmPassword
        )

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
            imeAction = ImeAction.Next
        )
        InputPassword(
            isError = !viewModel.isConfirmPasswordValid,
            value = viewModel.confirmPassword,
            labelText = R.string.confirm_password,
            onValueChange = viewModel::onConfirmPasswordValueChange,
            supportText = confirmPasswordInputSupportText,
            onDonePress = { viewModel.signUpUser(openAndPopUp) }
        )
        OutlinedButton(
            enabled = isButtonEnabled,
            onClick = { viewModel.signUpUser(openAndPopUp) }
        ) {
            if (!viewModel.isLoading) {
                Text(text = "Sign up")
            } else {
                ButtonLoader()
            }
        }
        Button(onClick = navigateToSignIn) {
            Text(text = "Go to sign in")
        }
    }
}
