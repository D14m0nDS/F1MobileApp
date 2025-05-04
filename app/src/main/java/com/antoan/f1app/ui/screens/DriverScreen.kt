package com.antoan.f1app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.antoan.f1app.api.models.Driver
import com.antoan.f1app.ui.components.LoadingScreen
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

                DriverContent(driver)
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
fun DriverContent(driver: Driver) {
    when {
        driver.id.isEmpty() -> LoadingScreen()
        else ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Driver Image
                Image(
                    painter = rememberAsyncImagePainter(driver.headshotUrl),
                    contentDescription = "Driver Image",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )

                // Driver Name
                Text(text = driver.name, style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(16.dp))

                // Driver Details
                InfoRow(label = "Age", value = driver.age.toString())
                InfoRow(label = "Number", value = driver.number.toString())
                InfoRow(label = "Nationality", value = driver.nationality)
                InfoRow(label = "Constructor", value = driver.constructorName)
                InfoRow(label = "Points", value = driver.points.toString())

                Spacer(modifier = Modifier.height(16.dp))

                // Race Results
                Text("Race Results:", style = MaterialTheme.typography.bodyLarge)
                Column(modifier = Modifier.fillMaxWidth()) {
                    driver.results.forEach { result ->
                        Text("- $result", style = MaterialTheme.typography.bodyMedium)
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