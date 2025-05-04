package com.antoan.f1app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.antoan.f1app.api.models.Constructor
import com.antoan.f1app.ui.components.LoadingScreen
import com.antoan.f1app.ui.components.TopNavBar
import com.antoan.f1app.ui.viewmodels.ConstructorScreenViewModel

@Composable
fun ConstructorScreen(viewModel: ConstructorScreenViewModel, constructorId: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopNavBar(
                screenTitle = "Constructor Info",
                onBack = onBack
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(padding),
            color = MaterialTheme.colorScheme.background
        ) {
            if(constructorId == "Null") {
                ConstructorErrorScreen(message = "Constructor could not be found")
            } else {

                LaunchedEffect(constructorId) {
                    viewModel.loadConstructorInfo(constructorId)
                }

                val constructor by viewModel.constructorInfo.collectAsState()
                val baseUrl = viewModel.baseUrl

                ConstructorContent( baseUrl = baseUrl, constructor = constructor)
            }
        }
    }
}

@Composable
fun ConstructorErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun ConstructorContent(baseUrl: String, constructor: Constructor) {
    val dpi = getDeviceDpi()

    when {
        constructor.id.isEmpty() -> LoadingScreen()
        else ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val imageUrl = remember(constructor.id, dpi) {
                    val teamLogoImage = constructor.id
                    "$baseUrl/f1/images/${teamLogoImage}_$dpi.png"
                }
                val painter = rememberAsyncImagePainter(model = imageUrl)

                Image(
                    painter = painter,
                    contentDescription = "Constructor Logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Constructor Name: ${constructor.name}",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
    }
}