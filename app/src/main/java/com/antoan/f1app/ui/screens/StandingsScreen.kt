package com.antoan.f1app.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.antoan.f1app.ui.components.ConstructorStandingsList
import com.antoan.f1app.ui.components.DriverStandingsList
import com.antoan.f1app.ui.components.StandingsNavBar
import com.antoan.f1app.ui.viewmodels.StandingsViewModel

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
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}