package com.example.msger.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.msger.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInput(
    isError: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    onValueClear: () -> Unit,
    @StringRes errorText: Int
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = null
            )
        },
        label = { Text(text = stringResource(id = R.string.email)) },
        singleLine = true,
        supportingText = { Text(text = stringResource(id = errorText)) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier.clickable(
                    onClickLabel = stringResource(id = R.string.input_clear_label),
                    role = Role.Button,
                    onClick = onValueClear
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    isError: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes errorText: Int
) {
    var isPasswordShown by rememberSaveable { mutableStateOf(false) }
    val visualTransformation =
        if (isPasswordShown) VisualTransformation.None else PasswordVisualTransformation()
    val trailingIcon =
        if (isPasswordShown) R.drawable.visibility_off_icon else R.drawable.visibility_icon

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.key_icon),
                contentDescription = null
            )
        },
        label = { Text(text = stringResource(id = R.string.password)) },
        singleLine = true,
        supportingText = { Text(text = stringResource(id = errorText)) },
        visualTransformation = visualTransformation,
        trailingIcon = {
            Icon(
                painter = painterResource(id = trailingIcon),
                contentDescription = null,
                modifier = Modifier.clickable(
                    onClickLabel = stringResource(id = R.string.input_show_value),
                    role = Role.Button,
                    onClick = { isPasswordShown = !isPasswordShown }
                )
            )
        }
    )
}