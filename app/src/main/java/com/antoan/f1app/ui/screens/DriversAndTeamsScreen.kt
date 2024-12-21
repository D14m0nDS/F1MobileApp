package com.antoan.f1app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun DriversAndTeamsScreen(
    onNavigateToDrivers: () -> Unit,
    onNavigateToConstructors: () -> Unit
) {
    Column {
        Button(onClick = onNavigateToDrivers) {
            Text("View Drivers")
        }
        Button(onClick = onNavigateToConstructors) {
            Text("View Constructors")
        }
    }
}
