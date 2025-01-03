package com.antoan.f1app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.antoan.f1app.ui.viewmodels.AllConstructorsViewModel

@Composable
fun AllConstructorsScreen(viewModel: AllConstructorsViewModel) {
    val constructors by viewModel.constructors.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn {
            items(constructors) { constructor ->
                Text(
                    text = constructor,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}
