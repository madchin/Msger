package com.example.msger.feature_authentication.presentation.util.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.msger.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputPassword(
    isError: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes supportText: Int,
    modifier: Modifier = Modifier,
    @StringRes labelText: Int = R.string.password,
    imeAction: ImeAction = ImeAction.Done,
    onDonePress: () -> Unit = {},
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
        label = { Text(text = stringResource(id = labelText)) },
        singleLine = true,
        supportingText = { Text(text = stringResource(id = supportText)) },
        visualTransformation = visualTransformation,
        trailingIcon = {
            Icon(
                painter = painterResource(id = trailingIcon),
                contentDescription = null,
                modifier = Modifier
                    .focusProperties { canFocus = false }
                    .clickable(
                        onClickLabel = stringResource(id = R.string.input_show_value),
                        role = Role.Button,
                        onClick = { isPasswordShown = !isPasswordShown }
                    )
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = {
            defaultKeyboardAction(ImeAction.Done)
            onDonePress()
        }),
        modifier = modifier.fillMaxWidth()
    )
}
