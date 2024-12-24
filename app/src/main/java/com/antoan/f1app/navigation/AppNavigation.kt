package com.antoan.f1app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.antoan.f1app.ui.components.BottomNavBar
import com.antoan.f1app.ui.screens.AllConstructorsScreen
import com.antoan.f1app.ui.screens.AllDriversScreen
import com.antoan.f1app.ui.screens.ConstructorScreen
import com.antoan.f1app.ui.screens.DriverScreen
import com.antoan.f1app.ui.screens.DriversAndTeamsScreen
import com.antoan.f1app.ui.screens.HomeScreen
import com.antoan.f1app.ui.screens.LoginScreen
import com.antoan.f1app.ui.screens.ProfileScreen
import com.antoan.f1app.ui.screens.StandingsScreen
import com.antoan.f1app.ui.viewmodels.ConstructorScreenViewModel
import com.antoan.f1app.ui.viewmodels.DriverScreenViewModel
import com.antoan.f1app.ui.viewmodels.LoginViewModel
import com.antoan.f1app.ui.viewmodels.ThemeViewModel

// App navigation through all screens
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    themeViewModel: ThemeViewModel,
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val isLoggedIn = loginViewModel.isLoggedIn()
        val startDestination = if (isLoggedIn) Destinations.Home.route else Destinations.Login.route

        Scaffold(
            bottomBar = {
                if(isLoggedIn) BottomNavBar(navController)
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = modifier.padding(paddingValues)
            ) {
                // Login screen
                composable(route = Destinations.Login.route) {
                    LoginScreen(onLoginSuccess = {
                        navController.navigate(Destinations.Home.route) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    })
                }

                // Home Screen
                composable(route = Destinations.Home.route) { HomeScreen() }

                // Drivers and constructors list
                composable(route = BottomNavDestinations.DriversAndTeams.route) {
                    DriversAndTeamsScreen(onNavigateToDrivers = {
                        navController.navigate(Destinations.Drivers.route)
                    }, onNavigateToConstructors = {
                        navController.navigate(Destinations.Constructors.route)
                    })
                }

                // Current standings in the season
                composable(route = Destinations.Standings.route) { StandingsScreen() }

                // Driver info
                composable(route = Destinations.Driver.route) { backStackEntry ->
                    val driverId = backStackEntry.arguments?.getString("id") ?: "Null"
                    val viewModel: DriverScreenViewModel = viewModel()
                    DriverScreen(viewModel = viewModel,driverId = driverId)
                }

                // Constructor info
                composable(route = Destinations.Constructor.route) { backStackEntry ->
                    val constructorId = backStackEntry.arguments?.getString("id") ?: "Null"
                    val viewModel: ConstructorScreenViewModel = viewModel()
                    ConstructorScreen(viewModel = viewModel,constructorId = constructorId)
                }

                // User profile
                composable(route = BottomNavDestinations.Profile.route) { ProfileScreen(themeViewModel)}

                // All drivers list, redirected from DriversAndTeamsScreen()
                composable(route = Destinations.Drivers.route) { AllDriversScreen() }

                // All constructors list, redirected from DriversAndTeamsScreen()
                composable(route = Destinations.Constructors.route) { AllConstructorsScreen() }
            }

        }
    }

}