package com.antoan.f1app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.antoan.f1app.api.models.DriverStandings
import com.antoan.f1app.navigation.Destinations
import com.antoan.f1app.ui.screens.getDeviceDpi
import com.antoan.f1app.ui.viewmodels.StandingsViewModel


@Composable
fun DriverStandingsList(driverStandings: List<DriverStandings>, navController: NavController, viewModel: StandingsViewModel) {
    val baseUrl = viewModel.baseUrl
    val dpi = getDeviceDpi()

    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Text("#", textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(0.5f))
                Text("Driver", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(2f).padding(start = 10.dp))
                Text("Team", textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Text("Points", textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Text("Wins", textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(0.75f))
            }
            HorizontalDivider()
        }
        items(driverStandings) { driver ->
            val imageUrl = remember(driver.constructorId, dpi) {
                val teamLogoImage = driver.constructorId
                "$baseUrl/f1/images/${teamLogoImage}_$dpi.png"
            }

            val painter = rememberAsyncImagePainter(model = imageUrl)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Destinations.Driver.route.replace("{id}", driver.driverId))
                    }
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(driver.position.toString(), textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(0.5f))
                    Text(driver.name, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(2f).padding(start = 10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                navController.navigate(Destinations.Constructor.route.replace("{id}", driver.constructorId))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = driver.constructorId,
                            modifier = Modifier
                                .height(24.dp)
                                .widthIn(max = 40.dp)
                                .wrapContentSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Text(driver.points.toString(), textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                    Text(driver.wins.toString(), textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(0.75f))
                }
            }

            HorizontalDivider()
        }
    }
}
