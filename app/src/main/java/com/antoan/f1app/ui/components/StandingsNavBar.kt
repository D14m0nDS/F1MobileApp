package com.antoan.f1app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun StandingsNavBar(
    pagerState: PagerState
) {
    val tabTitles = listOf("Drivers", "Constructors")

//    TabRow(
//        selectedTabIndex = pagerState.currentPage,
//    ) {
//    tabTitles.forEachIndexed { index, title ->
//        Tab(
//            text = { Text(title) },
//            selected = (pagerState.currentPage == index),
//            onClick = {
//                coroutineScope.launch {
//                    pagerState.animateScrollToPage(index)
//                }
//            }
//        )
//    }
}