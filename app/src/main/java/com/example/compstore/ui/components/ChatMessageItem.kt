package com.example.compstore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compstore.chatGPT.Message

@Composable
fun ChatMessageItem(message: Message) {
    val label = when (message.role) {
        "assistant" -> "Консультант"
        "user" -> "Покупатель"
        else -> message.role
    }

    val textAlignment = if (message.role == "assistant") TextAlign.Start else TextAlign.End

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                textAlign = textAlignment
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.content,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = textAlignment
            )
        }
    }
}