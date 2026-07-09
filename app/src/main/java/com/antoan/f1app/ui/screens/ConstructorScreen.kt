package com.antoan.f1app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.antoan.f1app.api.models.ConstructorInfo
import com.antoan.f1app.api.models.ConstructorInfoDriverResult
import com.antoan.f1app.api.models.ConstructorInfoRaceResult
import com.antoan.f1app.api.models.Race
import com.antoan.f1app.ui.components.LoadingScreen
import com.antoan.f1app.ui.components.TopNavBar
import com.antoan.f1app.ui.components.formatRaceOutcome
import com.antoan.f1app.ui.viewmodels.ConstructorScreenViewModel

@Composable
fun ConstructorScreen(viewModel: ConstructorScreenViewModel, constructorId: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopNavBar(
                screenTitle = "Constructor Info",
                onBack = onBack
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(padding),
            color = MaterialTheme.colorScheme.background
        ) {
            if (constructorId == "Null") {
                ConstructorErrorScreen(message = "Constructor could not be found")
            } else {
                LaunchedEffect(constructorId) {
                    viewModel.loadConstructorInfo(constructorId)
                }

                val constructor by viewModel.constructorInfo.collectAsState()
                val schedule by viewModel.schedule.collectAsState()

                ConstructorContent(
                    constructor = constructor,
                    races = schedule.races,
                    baseUrl = viewModel.baseUrl
                )
            }
        }
    }
}

@Composable
fun ConstructorErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun ConstructorContent(
    constructor: ConstructorInfo,
    races: List<Race>,
    baseUrl: String
) {
    when {
        constructor.id.isEmpty() -> LoadingScreen()
        else -> {
            val dpi = getDeviceDpi()
            val logoUrl = remember(constructor.id, dpi) {
                "$baseUrl/f1/images/${constructor.id}_$dpi.png"
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = constructor.name,
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = constructor.nationality,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Image(
                            painter = rememberAsyncImagePainter(model = logoUrl),
                            contentDescription = "Constructor Logo",
                            modifier = Modifier.height(64.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                if (constructor.raceResults.isEmpty()) {
                    item {
                        Text(text = "No race results", style = MaterialTheme.typography.bodyLarge)
                    }
                } else {
                    items(constructor.raceResults) { raceResult ->
                        val race = races.firstOrNull { it.id == raceResult.raceId }
                        val imageUrl = remember(race?.circuit?.name, dpi) {
                            race?.let {
                                val circuitImageName = it.circuit.name
                                    .normalizeToAscii()
                                    .lowercase()
                                    .replace(" ", "_")
                                    .replace("-", "_")
                                "$baseUrl/f1/images/${circuitImageName}_$dpi.png"
                            } ?: ""
                        }

                        ConstructorRaceCard(
                            raceResult = raceResult,
                            imageUrl = imageUrl
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ConstructorRaceCard(
    raceResult: ConstructorInfoRaceResult,
    imageUrl: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(220.dp)) {
            if (imageUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = raceResult.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Text(
                    text = raceResult.name,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DriverBlock(
                        result = raceResult.driverResults.getOrNull(0),
                        alignEnd = false,
                        modifier = Modifier.weight(1f).padding(end = 4.dp)
                    )
                    DriverBlock(
                        result = raceResult.driverResults.getOrNull(1),
                        alignEnd = true,
                        modifier = Modifier.weight(1f).padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(0.2f))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total Points:",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = formatPoints(raceResult.totalPoints),
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun DriverBlock(
    result: ConstructorInfoDriverResult?,
    alignEnd: Boolean,
    modifier: Modifier = Modifier
) {
    val alignment = if (alignEnd) Alignment.End else Alignment.Start
    val textAlign = if (alignEnd) TextAlign.End else TextAlign.Start

    Column(
        modifier = modifier,
        horizontalAlignment = alignment
    ) {
        Text(
            text = result?.name.orEmpty(),
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Position: ${result?.position?.toInt() ?: 0}",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Points: ${formatPoints(result?.points ?: 0f)}",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = result?.let {
                formatRaceOutcome(
                    position = it.position,
                    time = it.time,
                    status = it.status,
                    showWinnerTotalTime = false
                )
            }.orEmpty(),
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun formatPoints(value: Float): String {
    return if (value == value.toInt().toFloat()) {
        value.toInt().toString()
    } else {
        value.toString()
    }
}
