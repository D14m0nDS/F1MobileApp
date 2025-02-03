package com.antoan.f1app.ui.screens

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.navigation.Destinations
import com.antoan.f1app.ui.components.LoadingScreen
import com.antoan.f1app.ui.viewmodels.HomeScreenViewModel
import java.text.Normalizer
import java.util.regex.Pattern

fun String.normalizeToAscii(): String {
    val normalized = Normalizer.normalize(this, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    return pattern.matcher(normalized).replaceAll("")
}

fun getDeviceDpi(): String {
    val density = Resources.getSystem().displayMetrics.densityDpi
    return when {
        density <= 160 -> "mdpi"
        density <= 240 -> "hdpi"
        density <= 320 -> "xhdpi"
        density <= 480 -> "xxhdpi"
        else -> "xxxhdpi"
    }
}


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navController: NavController
) {
    val schedule by viewModel.schedule.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val baseUrl = viewModel.baseUrl
    val dpi = getDeviceDpi()

    if (isLoading) {
        LoadingScreen()
    } else {
        Column(
            modifier = Modifier.fillMaxSize()

        ) {
            Text(
                text = "Season: ${schedule.season}",
                modifier = Modifier.padding(16.dp)

            )
            if(schedule.races.isEmpty()) {
                Text(
                    text = "No races available",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(schedule.races.size) { index ->
                        val race = schedule.races[index]

                        val circuitImageName = race.circuit.name
                            .normalizeToAscii()
                            .lowercase()
                            .replace(" ", "_")
                            .replace("-", "_")

                        // Replace letters here
                        val imageUrl = "$baseUrl/f1/images/${circuitImageName}_$dpi.png"

                        Button(
                            onClick = {
                                val season = schedule.season.toString()
                                val round = race.round
                                navController.navigate(Destinations.Race.createRaceRoute(season, round))
                            },
                            shape = RoundedCornerShape(10),
                            modifier = Modifier
                                .padding(0.dp)
                                .fillMaxWidth()
                                .height(200.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(10))

                            ) {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "Circuit Image",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = schedule.races[index].name,
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    color = Color.White

                                )
                            }
                        }
                    }
                }
            }
        }
    }
}