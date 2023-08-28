package com.example.msger.feature_chat.presentation.util.component

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.msger.feature_chat.domain.model.Message
import java.util.Date
import java.util.Locale

@Composable
fun ChatMessage(
    message: Message,
    modifier: Modifier = Modifier
) {
    val sendTime: String =
        SimpleDateFormat(
            "MM/dd/yyyy HH:mm:ss",
            Locale.getDefault()
        ).format(Date((message.timestamp) * 1000))
    Column(modifier = modifier.padding(bottom = 16.dp)) {
        Row {
            Text(text = message.sender)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = sendTime)
        }
        Text(text = message.content, modifier = Modifier.padding(top = 8.dp))
    }
}