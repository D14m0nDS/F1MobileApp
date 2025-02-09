package com.antoan.f1app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.antoan.f1app.ui.components.StandingsNavBar
import com.antoan.f1app.ui.viewmodels.StandingsViewModel

@Composable
fun StandingsScreen(
    viewModel: StandingsViewModel
) {
    val driverStandings by viewModel.driverStandings.collectAsState()
    val constructorStandings by viewModel.constructorStandings.collectAsState()

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })

//    Scaffold(
//        topBar = {
//            StandingsNavBar(pagerState = pagerState)
//        }
//    ) { paddingValues ->
//        HorizontalPager(
//            state = pagerState
//        ) {
//            LazyColumn(
//                Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues)
//            ) {
//              items()
//            }
//        }
//    }
}
