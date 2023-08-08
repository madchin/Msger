package com.example.msger.feature_chat_manage.presentation.util.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import com.example.msger.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputUsername(
    isError: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    onValueClear: () -> Unit,
    @StringRes supportText: Int,
    modifier: Modifier = Modifier,
    @StringRes labelText: Int = R.string.user_name,
    imeAction: ImeAction = ImeAction.Done,
    onDonePress: () -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.person_icon),
                contentDescription = null
            )
        },
        label = { Text(text = stringResource(id = labelText)) },
        singleLine = true,
        supportingText = { Text(text = stringResource(id = supportText)) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier
                    .focusProperties { canFocus = false }
                    .clickable(
                        onClickLabel = stringResource(id = R.string.input_clear_label),
                        role = Role.Button,
                        onClick = onValueClear
                    )
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = {
            defaultKeyboardAction(ImeAction.Done)
            onDonePress()
        }),
        modifier = modifier.fillMaxWidth()
    )
}