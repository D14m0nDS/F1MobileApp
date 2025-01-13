package com.antoan.f1app.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.antoan.f1app.R
import com.antoan.f1app.ui.viewmodels.ConstructorScreenViewModel

@Composable
fun ConstructorScreen(viewModel: ConstructorScreenViewModel, constructorId: String) {
    if(constructorId == "Null") {
        ConstructorErrorScreen(message = "Constructor could not be found")
    } else {
        ConstructorContent(constructorId)
    }
}

@Composable
fun ConstructorErrorScreen(message: String) {
    Text(text = message)
}

@Composable
fun ConstructorContent(constructorId: String) {
}