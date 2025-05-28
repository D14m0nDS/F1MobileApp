package com.antoan.f1app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.HorizontalPager
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun StandingsSkeleton(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(state = pagerState, modifier = modifier) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(count = 20) {
                // Entire row is “invisible” except for the highlight
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .placeholder(
                            visible   = true,
                            color     = Color.Transparent,               // row background is transparent
                            highlight = PlaceholderHighlight.shimmer()   // white shimmer passes over it
                        )
                )
                HorizontalDivider(thickness = 1.dp)
            }
        }
    }
}