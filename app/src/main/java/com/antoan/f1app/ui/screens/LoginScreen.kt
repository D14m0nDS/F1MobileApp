package com.antoan.f1app.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.Text

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    Button(onClick = onLoginSuccess) {
        Text("Log in with Google")
    }
}