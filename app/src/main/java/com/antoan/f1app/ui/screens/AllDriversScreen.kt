package com.antoan.f1app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.antoan.f1app.ui.components.TopNavBar
import com.antoan.f1app.ui.viewmodels.AllDriversViewModel


@Composable
fun AllDriversScreen(
    viewModel: AllDriversViewModel,
    navController: NavController
) {
    val drivers by viewModel.drivers.collectAsState()

    Scaffold(
        topBar = {
            TopNavBar(navController, "Drivers")
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopStart
        ) {
            LazyColumn {
                items(drivers.size) { index ->
                    Text(
                        text = drivers[index].name,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
                    )
                }
            }
        }
    }
}