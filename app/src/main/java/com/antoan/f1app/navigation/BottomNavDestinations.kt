package com.antoan.f1app.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Group

enum class BottomNavDestinations(val route: String, val label: String, val icon: ImageVector) {
    Home("home", "Home", Icons.Default.Home),
    Standings("standings", "Standings", Icons.Default.Star),
    DriversAndTeams("drivers_and_teams", "Teams", Icons.Default.Group),
    Profile("profile", "Profile", Icons.Default.Person)
}
