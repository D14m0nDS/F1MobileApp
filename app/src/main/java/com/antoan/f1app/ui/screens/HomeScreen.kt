package com.antoan.f1app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.antoan.f1app.ui.components.LoadingScreen
import com.antoan.f1app.ui.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel
) {
    val schedule by viewModel.schedule.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        LoadingScreen()
    } else {
        Column(
            modifier = Modifier.fillMaxSize()

        ) {
            Text(
                text = "Season: ${schedule.season}",
                modifier = Modifier.padding(16.dp)

            )
            if(schedule.races.isEmpty()) {
                Text(
                    text = "No races available",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(schedule.races.size) { index ->
                        Box(
                            modifier = Modifier.padding(10.dp)
                                .background(color = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = schedule.races[index].name,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }
    }
}