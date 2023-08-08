package com.example.msger.feature_chat_manage.presentation.chat_list.component

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.msger.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListItem(
    headlineText: String,
    supportingText: String,
    onItemClick: () -> Unit
) {
    ListItem(
        headlineText = {
            Text(text = headlineText)
        },
        supportingText = {
            Text(text = supportingText)
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        },
        modifier = Modifier.clickable(
            onClickLabel = stringResource(id = R.string.enter_chat_label),
            onClick = onItemClick
        )
    )
}