package com.example.compstore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compstore.ui.theme.PrimaryText

@Composable
fun MenuItemCategory(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageResId: Int? = null
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (imageResId != null) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = name,
                modifier = Modifier
                    .size(96.dp)
                    .padding(bottom = 8.dp)
            )
        }
        Text(
            text = name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 16.sp,
                color = PrimaryText // Основной текст – темно-серый
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            softWrap = true
        )
    }
}