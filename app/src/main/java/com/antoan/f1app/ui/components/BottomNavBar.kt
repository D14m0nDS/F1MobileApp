package com.antoan.f1app.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.antoan.f1app.navigation.BottomNavDestinations
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavDestinations.Home,
        BottomNavDestinations.Standings,
        BottomNavDestinations.DriversAndTeams,
        BottomNavDestinations.Profile
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { destination ->
            NavigationBarItem (
                icon = { Icon(imageVector = destination.icon, contentDescription = destination.name) },
                label = { Text(destination.name) },
                selected = currentRoute == destination.route,
                onClick = {
                    if (currentRoute != destination.route) {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
