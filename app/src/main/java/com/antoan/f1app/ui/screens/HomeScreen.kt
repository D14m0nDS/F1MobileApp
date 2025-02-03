package com.antoan.f1app.ui.screens

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
import com.antoan.f1app.navigation.Destinations
import com.antoan.f1app.ui.components.LoadingScreen
import com.antoan.f1app.ui.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navController: NavController
) {
    val schedule by viewModel.schedule.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

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

                        val context = LocalContext.current
                        val circuitImageName = schedule.races[index].circuit.name
                            .lowercase()
                            .replace(" ", "_")

                        val imageResId = context.resources.getIdentifier(
                            circuitImageName, "mipmap", context.packageName
                        )

                        Button(
                            onClick = {
                                val season = schedule.season.toString()
                                val round = schedule.races[index].round
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
                                Image(
                                    painter = if (imageResId != 0) painterResource(id = imageResId)
                                    else painterResource(id = com.antoan.f1app.R.mipmap.baku_city_circuit),
                                    contentDescription = null,
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