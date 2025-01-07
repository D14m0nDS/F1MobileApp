package com.antoan.f1app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.antoan.f1app.navigation.BottomNavDestinations
import androidx.compose.material3.NavigationBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Global navigation bar for the app
@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavDestinations.Home,
        BottomNavDestinations.Standings,
        BottomNavDestinations.DriversAndTeams,
        BottomNavDestinations.Profile
    )

    NavigationBar(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { destination ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        if (currentRoute == destination.route) Color.Gray.copy(alpha = 0.3f) else Color.Transparent
                    )
                    .clickable(onClick = {
                        if (currentRoute != destination.route) {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.name,
                        tint = if (currentRoute == destination.route) Color.Black else Color.Gray
                    )
                    Text(
                        text = destination.label,
                        color = if (currentRoute == destination.route) Color.Black else Color.Gray
                    )
                }
            }
        }
    }
}
