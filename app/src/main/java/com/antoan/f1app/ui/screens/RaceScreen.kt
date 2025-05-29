package com.antoan.f1app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.antoan.f1app.ui.components.TopNavBar
import com.antoan.f1app.ui.components.LoadingScreen
import com.antoan.f1app.ui.viewmodels.RaceScreenViewModel

private fun parseRaceTimeMs(raw: String?): Long? {
    if (raw.isNullOrBlank()) return null

    val parts = raw
        .substringAfter("days")
        .trim()
        .split('.', limit = 2)

    val hmsPart  = parts.getOrNull(0) ?: return null
    val fracPart = parts.getOrNull(1)?.take(3).orEmpty()

    val timePieces = hmsPart.split(':').mapNotNull { it.toLongOrNull() }
    if (timePieces.size != 3) return null

    val (h, m, s) = timePieces
    val fracMs    = fracPart.padEnd(3, '0').toLong()
    return h * 3_600_000 +
            m *   60_000 +
            s *    1_000 +
            fracMs
}

private fun formatWinner(ms: Long): String {
    val h    = ms / 3_600_000
    val m    = (ms % 3_600_000) / 60_000
    val s    = (ms %    60_000) / 1_000
    val frac = ms % 1_000

    return if (h > 0) {
        "%d:%02d:%02d.%03d".format(h, m, s, frac)
    } else {
        "%02d:%02d.%03d".format(m, s, frac)
    }
}

private fun formatDelta(deltaMs: Long): String {
    val m    = deltaMs / 60_000
    val s    = (deltaMs % 60_000) / 1_000
    val frac = deltaMs % 1_000

    return if (m > 0) {
        "+%d:%02d.%03d".format(m, s, frac)
    } else {
        "+%d.%03d".format(s, frac)
    }
}

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

            itemsIndexed(race.results) { idx, result ->
                val ms = parseRaceTimeMs(result.time)

                val displayTime = when {
                    idx == 0 && ms != null ->
                        formatWinner(ms)

                    ms == null ->
                        result.status

                    else ->
                        formatDelta(ms) + "s"
                }

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
