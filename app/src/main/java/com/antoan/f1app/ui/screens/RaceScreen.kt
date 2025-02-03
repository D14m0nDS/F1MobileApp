package com.antoan.f1app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.antoan.f1app.ui.viewmodels.RaceScreenViewModel

@Composable
fun RaceScreen(
    viewModel: RaceScreenViewModel,
    season: String,
    round: Int
) {
    LaunchedEffect(Unit) {
        viewModel.loadRaceInfo(season, round)
    }

    val race by viewModel.race.collectAsState()

    Column {
        Text(text = "Race: ${race.name}")
        Text(text = "Round: $round - Season: $season")
    }
}
