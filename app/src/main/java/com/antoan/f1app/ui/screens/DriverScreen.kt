package com.antoan.f1app.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.antoan.f1app.ui.viewmodels.DriverScreenViewModel

@Composable
fun DriverScreen(viewModel: DriverScreenViewModel, driverId: String) {
    if(driverId == "Null") {
        DriverErrorScreen(message = "Driver could not be found")
    } else {
        DriverContent(driverId)
    }

}

@Composable
fun DriverErrorScreen(message: String) {
    Text(text = message)
}

@Composable
fun DriverContent(driverId: String) {

}