package com.antoan.f1app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.antoan.f1app.api.models.Result
import com.antoan.f1app.ui.screens.getDeviceDpi

@Composable
fun ResultItem(
    result: Result,
    baseUrl: String,
    displayTime: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors   = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = result.position.toInt().toString(),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(0.5f)
            )
            Text(
                text = result.driverName,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .wrapContentSize()
            ) {
                val dpi    = getDeviceDpi()
                val logoUrl = "$baseUrl/f1/images/${result.constructorId}_$dpi.png"

                AsyncImage(
                    model = logoUrl,
                    contentDescription = result.constructorId,
                    modifier = Modifier
                        .height(24.dp)
                        .widthIn(max = 40.dp)
                        .wrapContentSize(),
                    contentScale = ContentScale.Fit
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(1.5f)
            ) {
                Text(
                    text = displayTime,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${result.points} pts",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
