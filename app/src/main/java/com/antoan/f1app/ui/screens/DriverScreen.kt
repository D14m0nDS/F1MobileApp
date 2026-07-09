package com.antoan.f1app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import coil.compose.rememberAsyncImagePainter
import com.antoan.f1app.api.models.Driver
import com.antoan.f1app.ui.components.LoadingScreen
import com.antoan.f1app.ui.components.formatResultDisplayText
import com.antoan.f1app.ui.components.TopNavBar
import com.antoan.f1app.ui.viewmodels.DriverScreenViewModel

@Composable
fun DriverScreen(viewModel: DriverScreenViewModel, driverId: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopNavBar(
                screenTitle = "Driver Info",
                onBack = onBack
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(padding),
            color = MaterialTheme.colorScheme.background
        ) {
            if (driverId == "Null") {
                DriverErrorScreen(message = "Driver could not be found")
            } else {

                LaunchedEffect(driverId) {
                    viewModel.loadDriverInfo(driverId)
                }

                val driver by viewModel.driverInfo.collectAsState()
                val schedule by viewModel.schedule.collectAsState()
                val baseUrl = viewModel.baseUrl

                DriverContent(driver, schedule, baseUrl)
            }
        }
    }
}

@Composable
fun DriverErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun DriverContent(driver: Driver, schedule: com.antoan.f1app.api.models.Schedule, baseUrl: String) {
    when {
        driver.id.isEmpty() -> LoadingScreen()
        else -> {
            val dpi = getDeviceDpi()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Image(
                        painter = rememberAsyncImagePainter(driver.headshotUrl),
                        contentDescription = "Driver Image",
                        modifier = Modifier
                            .size(150.dp)
                            .padding(8.dp),
                        contentScale = ContentScale.Crop
                    )

                    Text(text = driver.name, style = MaterialTheme.typography.headlineMedium)

                    Spacer(modifier = Modifier.height(16.dp))

                    InfoRow(label = "Age", value = driver.age.toString())
                    InfoRow(label = "Number", value = driver.number.toString())
                    InfoRow(label = "Nationality", value = driver.nationality)
                    InfoRow(label = "Constructor", value = driver.constructorName)
                    InfoRow(label = "Points", value = driver.points.toString())

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Race Results:", style = MaterialTheme.typography.bodyLarge)
                }

                if (driver.results.isEmpty()) {
                    item {
                        Text(text = "No race results", modifier = Modifier.padding(8.dp))
                    }
                } else {
                    items(driver.results) { result ->
                        val race = schedule.races.firstOrNull { it.id == result.raceId }
                        val displayTime = formatResultDisplayText(result, showWinnerTotalTime = false)

                        val imageUrl = remember(race?.circuit?.name, dpi) {
                            race?.let {
                                val circuitImageName = it.circuit.name
                                    .normalizeToAscii()
                                    .lowercase()
                                    .replace(" ", "_")
                                    .replace("-", "_")
                                "${baseUrl}f1/images/${circuitImageName}_$dpi.png"
                            } ?: ""
                        }

                        Button(
                            onClick = { },
                            shape = RoundedCornerShape(10),
                            modifier = Modifier
                                .padding(vertical = 6.dp)
                                .fillMaxWidth()
                                .height(140.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Box(modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(10))) {
                                if (imageUrl.isNotEmpty()) {
                                    val painter = rememberAsyncImagePainter(model = imageUrl)
                                    Image(
                                        painter = painter,
                                        contentDescription = "Circuit Image",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.Gray)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.4f))
                                )

                                Text(
                                    text = race?.name ?: result.raceId,
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .padding(top = 10.dp, start = 12.dp, end = 12.dp),
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Text(
                                    text = "Position: ${result.position.toInt()}",
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    color = Color.White,
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                Row(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = displayTime,
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "Points: ${result.points}",
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}


@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}