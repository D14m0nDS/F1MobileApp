package com.antoan.f1app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.antoan.f1app.ui.components.LoadingScreen
import com.antoan.f1app.ui.components.TopNavBar
import com.antoan.f1app.ui.viewmodels.RaceScreenViewModel

@Composable
fun RaceScreen(
    viewModel: RaceScreenViewModel,
    season: String,
    round: Int,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.loadRaceInfo(season, round)
    }

    val isLoading by viewModel.isLoading.collectAsState()
    val race by viewModel.race.collectAsState()

    Scaffold(
        topBar = {
            TopNavBar(navController, race.name)
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
                Column {
                    Text(text = "Race: ${race.name}")
                    Text(text = "Round: $round - Season: $season")
                }
            }
        }


    }

}
