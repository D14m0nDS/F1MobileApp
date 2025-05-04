package com.antoan.f1app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.antoan.f1app.ui.components.LoadingScreen
import com.antoan.f1app.ui.components.TopNavBar
import com.antoan.f1app.ui.viewmodels.RaceScreenViewModel
import com.antoan.f1app.api.models.Result
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

@Composable
fun RaceScreen(
    viewModel: RaceScreenViewModel,
    season: String,
    round: Int,
    onBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.loadRaceInfo(season, round)
    }

    val isLoading by viewModel.isLoading.collectAsState()
    val race by viewModel.race.collectAsState()

    Scaffold(
        topBar = {
            TopNavBar(onBack, race.name)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isLoading) {
                LoadingScreen()
            } else {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()) // Enables scrolling
                ) {
                    Text(text = "Race: ${race.name}", style = MaterialTheme.typography.headlineMedium)
                    Text(text = "Round: $round - Season: $season")
                    Text(text = "Circuit: ${race.circuit.name}")
                    Text(text = "Location: ${race.circuit.location.city}, ${race.circuit.location.country}")
                    Text(text = "Date: ${race.date}")

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Race Results:", style = MaterialTheme.typography.headlineSmall)

                    race.results.forEach { result ->
                        ResultCard(result = result)
                        Spacer(modifier = Modifier.height(8.dp)) // Adds space between cards
                    }
                }
            }
        }
    }
}

@Composable
fun ResultCard(result: Result) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Position: ${result.position.toInt()} - Driver: ${result.driverName} (${result.constructorName})", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Time: ${result.time}")
            Text(text = "Points: ${result.points.toInt()} - Status: ${result.status}")
        }
    }
}
