package com.antoan.f1app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun StandingsNavBar(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
    ) {
        TabItem(
            text = "Drivers",
            selected = pagerState.currentPage == 0,
            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(0) } },
            modifier = Modifier.weight(0.5f)
        )
        TabItem(
            text = "Constructors",
            selected = pagerState.currentPage == 1,
            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) } },
            modifier = Modifier.weight(0.5f)
        )
    }
}

@Composable
fun TabItem(text: String, selected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp)
            .fillMaxHeight(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}
