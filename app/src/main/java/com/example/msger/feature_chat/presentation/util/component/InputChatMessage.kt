package com.example.msger.feature_chat.presentation.util.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
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
fun InputChatMessage(
    isError: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes supportText: Int,
    modifier: Modifier = Modifier,
    @StringRes labelText: Int = R.string.send_chat_message,
    imeAction: ImeAction = ImeAction.Send,
    onDonePress: () -> Unit = {},
) {
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
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null,
                modifier = Modifier
                    .focusProperties { canFocus = false }
                    .clickable(
                        onClickLabel = stringResource(id = R.string.input_clear_label),
                        role = Role.Button,
                        onClick = onDonePress
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