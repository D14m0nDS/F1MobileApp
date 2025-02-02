package com.antoan.f1app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.antoan.f1app.ui.viewmodels.StandingsViewModel

@Composable
fun StandingsScreen(
    viewModel: StandingsViewModel
) {
    val driverStandings by viewModel.driverStandings.collectAsState()
    val constructorStandings by viewModel.constructorStandings.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Drivers",
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.White)

        )
        LazyColumn {
            items(driverStandings) { driver ->
                Text(
                    text = driver.name + " " + driver.constructorName + " " + driver.points,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
                )
            }
        }
        Text(
            text = "Constructors",
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.White)
        )
        LazyColumn {
            items(constructorStandings) { constructor ->
                Text(
                    text = constructor.name + " " + constructor.points,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
                )
            }
        }
    }
}