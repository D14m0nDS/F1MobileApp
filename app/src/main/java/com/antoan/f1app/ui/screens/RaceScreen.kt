package com.antoan.f1app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.antoan.f1app.ui.components.RaceHeader
import com.antoan.f1app.ui.components.ResultItem
import com.antoan.f1app.ui.components.formatResultDisplayText
import com.antoan.f1app.ui.components.TopNavBar
import com.antoan.f1app.ui.components.LoadingScreen
import com.antoan.f1app.ui.viewmodels.RaceScreenViewModel

@Composable
fun RaceScreen(
    viewModel: RaceScreenViewModel,
    season: String,
    round: Int,
    onBack: () -> Unit
) {
    LaunchedEffect(round) {
        viewModel.loadRaceInfo(season, round)
    }

    val isLoading by viewModel.isLoading.collectAsState()
    val race       by viewModel.race.collectAsState()
    val baseUrl    = viewModel.baseUrl
    val dpi        = getDeviceDpi()

    Scaffold(
        topBar = { TopNavBar(onBack, screenTitle = "") }
    ) { innerPadding ->
        if (isLoading) {
            LoadingScreen(Modifier.padding(innerPadding))
            return@Scaffold
        }

        val displayDate = race.date.substringBefore("T")

        val imageUrl = remember(race.circuit.name, dpi) {
            val circuitImageName = race.circuit.name
                .normalizeToAscii()
                .lowercase()
                .replace(" ", "_")
                .replace("-", "_")
            "$baseUrl/f1/images/${circuitImageName}_$dpi.png"
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 1) the photo header
            item {
                RaceHeader(
                    race     = race.copy( date = displayDate ),
                    photoUrl = imageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                )
                Spacer(Modifier.height(16.dp))
            }

            item {
                Text(
                    text = "Race Results",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                )
            }

            items(race.results) { result ->
                val displayTime = formatResultDisplayText(
                    result = result,
                    showWinnerTotalTime = result.position == 1f
                )

                ResultItem(
                    result      = result,
                    displayTime = displayTime,
                    baseUrl     = baseUrl,
                    modifier    = Modifier
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            item {
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}
