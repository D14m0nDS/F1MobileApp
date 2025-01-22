package com.antoan.f1app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.antoan.f1app.R

// Screen with two buttons, each for either all drivers or constructors list
@Composable
fun DriversAndTeamsScreen(
    onNavigateToDrivers: () -> Unit,
    onNavigateToConstructors: () -> Unit
) {
    Column {

        // Driver Button
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .clickable(onClick = onNavigateToDrivers)
                .background(Color.Blue),
            contentAlignment = Alignment.Center
        ) {

            // Background Image
            Image(
                painter = painterResource(id = R.mipmap.drivers_button_background),
                contentDescription = "Drivers Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // A darker layer over the image, to make the text easier to read
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )

            Text(
                text = "View Drivers",
                style = MaterialTheme.typography.headlineMedium.copy(color = Color.White)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        //Constructor Button
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .clickable(onClick = onNavigateToConstructors)
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {

            // Background Image
            Image(
                painter = painterResource(id = R.mipmap.constructors_button_background),
                contentDescription = "Constructors Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // A darker layer over the image, to make the text easier to read
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )

            Text(
                text = "View Constructors",
                style = MaterialTheme.typography.headlineMedium.copy(color = Color.White)
            )
        }
    }
}
