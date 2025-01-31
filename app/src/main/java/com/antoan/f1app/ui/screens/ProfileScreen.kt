package com.antoan.f1app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.antoan.f1app.ui.viewmodels.AuthViewModel
import com.antoan.f1app.ui.viewmodels.ThemeViewModel

@Composable
fun ProfileScreen(themeViewModel: ThemeViewModel, authViewModel: AuthViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Profile Page", modifier = Modifier.padding(bottom = 24.dp))
        Button(onClick = { themeViewModel.toggleTheme() }) {
            Text(text = if (themeViewModel.isDarkTheme.value) "Switch to Light Theme" else "Switch to Dark Theme")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { authViewModel.logout() }) {
            Text("Logout")
        }
    }
}
