package com.antoan.f1app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.antoan.f1app.api.models.ConstructorStandings
import com.antoan.f1app.api.models.DriverStandings
import com.antoan.f1app.navigation.Destinations
import com.antoan.f1app.ui.components.LoadingScreen
import com.antoan.f1app.ui.components.StandingsNavBar
import com.antoan.f1app.ui.viewmodels.StandingsViewModel
import com.antoan.f1app.ui.components.VerticalDivider

@Composable
fun StandingsScreen(viewModel: StandingsViewModel, navController: NavController) {
    val driverStandings by viewModel.driverStandings.collectAsState()
    val constructorStandings by viewModel.constructorStandings.collectAsState()

    val pagerState =     rememberPagerState(initialPage = 0, pageCount = { 2 })

    Scaffold(
        topBar = {
            StandingsNavBar(pagerState = pagerState)
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding((paddingValues))
                .padding(bottom = 0.dp)

        ) { page ->
            when (page) {
                0 -> DriverStandingsList(
                    driverStandings = driverStandings,
                    navController = navController,
                    viewModel = viewModel
                )
                1 -> ConstructorStandingsList(
                    constructorStandings = constructorStandings,
                    navController = navController
                )
            }
        }
    }
}



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
                    .padding(vertical = 8.dp, horizontal = 20.dp)
            ) {
                Text("Driver", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(2f))
                Text("Points", textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Text("Wins", textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Text("Team", textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
            }
            HorizontalDivider()
        }
        items(driverStandings) { item ->
            val imageUrl = remember(item.constructorId, dpi) {
                val teamLogoImage = item.constructorId
                "$baseUrl/f1/images/${teamLogoImage}_$dpi.png"
            }

            val painter = rememberAsyncImagePainter(model = imageUrl)


            if (painter.state is AsyncImagePainter.State.Loading) {
                LoadingScreen()
            }

            Button(
                onClick = {
                    navController.navigate(Destinations.Driver.route.replace("{id}", item.driverId))
                },
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.onSurface)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(vertical = 8.dp, horizontal = 20.dp)
                ) {

                    Text(item.name, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(2f))
                    Text(item.points.toString(), textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                    Text(item.wins.toString(), textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = item.constructorId,
                            modifier = Modifier
                                .height(24.dp)  // Keep a consistent height
                                .widthIn(max = 40.dp)  // Limit max width to prevent wider logos from appearing too large
                                .wrapContentSize(),  // Ensures proper aspect ratio
                            contentScale = ContentScale.Fit
                        )
                    }

                }
            }
            HorizontalDivider()
        }
    }
}

@Composable
fun ConstructorStandingsList(constructorStandings: List<ConstructorStandings>, navController: NavController) {
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text("Constructor", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp).weight(2f))
                VerticalDivider()
                Text("Points", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp).weight(1f))
                VerticalDivider()
                Text("Wins", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp).weight(1f))
            }
            HorizontalDivider()
        }
        items(constructorStandings) { constructor ->
            Button(
                onClick = {
                    navController.navigate(Destinations.Constructor.route.replace("{id}", constructor.constructorId))
                },
                modifier = Modifier
                        .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.onSurface)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(constructor.name, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(2f))
                    VerticalDivider()
                    Text(constructor.points.toString(), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                    VerticalDivider()
                    Text(constructor.wins.toString(), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                }
            }
            HorizontalDivider()
        }
    }
}
