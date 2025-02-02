package com.antoan.f1app.ui.screens

import androidx.compose.foundation.layout.*
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
import com.antoan.f1app.ui.viewmodels.AllConstructorsViewModel

@Composable
fun AllConstructorsScreen(
    viewModel: AllConstructorsViewModel,
    navController: NavController
) {
    val constructors by viewModel.constructors.collectAsState()

    Scaffold(
        topBar = {
            TopNavBar(navController, "Constructors")
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopStart
        ) {
            LazyColumn {
                items(constructors.size) { index ->
                    Text(
                        text = constructors[index].name,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
                    )
                }
            }
        }
    }

}
