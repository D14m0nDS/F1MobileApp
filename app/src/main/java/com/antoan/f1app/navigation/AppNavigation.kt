package com.antoan.f1app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.antoan.f1app.ui.components.BottomNavBar
import com.antoan.f1app.ui.screens.AllConstructorsScreen
import com.antoan.f1app.ui.screens.AllDriversScreen
import com.antoan.f1app.ui.screens.ConstructorScreen
import com.antoan.f1app.ui.screens.DriverScreen
import com.antoan.f1app.ui.screens.DriversAndTeamsScreen
import com.antoan.f1app.ui.screens.HomeScreen
import com.antoan.f1app.ui.screens.LoginScreen
import com.antoan.f1app.ui.screens.ProfileScreen
import com.antoan.f1app.ui.screens.RaceScreen
import com.antoan.f1app.ui.screens.RegisterScreen
import com.antoan.f1app.ui.screens.StandingsScreen
import com.antoan.f1app.ui.viewmodels.ConstructorScreenViewModel
import com.antoan.f1app.ui.viewmodels.DriverScreenViewModel
import com.antoan.f1app.ui.viewmodels.ThemeViewModel
import com.antoan.f1app.ui.viewmodels.AllConstructorsViewModel
import com.antoan.f1app.ui.viewmodels.AllDriversViewModel
import com.antoan.f1app.ui.viewmodels.AuthViewModel
import com.antoan.f1app.ui.viewmodels.HomeScreenViewModel
import com.antoan.f1app.ui.viewmodels.RaceScreenViewModel
import com.antoan.f1app.ui.viewmodels.StandingsViewModel

// App navigation through all screens
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val themeViewModel: ThemeViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel() // We need to change this to someting like authViewModel
    val isLoggedIn = authViewModel.isLoggedIn.value
    //A box filling the screen, for cleaner look
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val startDestination = if (isLoggedIn) Destinations.Home.route else Destinations.Login.route

        // Scaffold, containing the navigation bar and the current screen
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
                    LoginScreen(navController, authViewModel)
                }

                //Register screen
                composable(route = Destinations.Register.route) {
                    RegisterScreen(navController, authViewModel)
                }
                
                // Home Screen
                composable(route = Destinations.Home.route) {
                    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
                    HomeScreen(
                        viewModel = homeScreenViewModel,
                        navController = navController
                    )
                }

                // Race info screen
                composable(
                    route = Destinations.Race.route,
                    arguments = listOf(
                        navArgument("season") { type = NavType.StringType },
                        navArgument("round") { type = NavType.IntType }
                    )

                ) { backStackEntry ->
                    val season = backStackEntry.arguments?.getString("season") ?: "2024"
                    val round = backStackEntry.arguments?.getInt("round") ?: 1

                    val raceScreenViewModel: RaceScreenViewModel = hiltViewModel()
                    RaceScreen(viewModel = raceScreenViewModel, season = season, round = round, onBack = { navController.popBackStack() })
                }
                // Drivers and constructors list
                composable(route = BottomNavDestinations.DriversAndTeams.route) {
                    DriversAndTeamsScreen(
                        onNavigateToDrivers = {
                            navController.navigate(Destinations.Drivers.route)
                        },
                        onNavigateToConstructors = {
                            navController.navigate(Destinations.Constructors.route)
                        }
                    )
                }

                // Current standings in the season
                composable(route = Destinations.Standings.route) {
                    val standingsViewModel : StandingsViewModel = hiltViewModel()
                    StandingsScreen(viewModel = standingsViewModel, navController = navController)
                }

                // Driver details
                composable(route = Destinations.Driver.route, arguments = listOf(navArgument("id") { type = NavType.StringType })) { backStackEntry ->
                    val driverId = backStackEntry.arguments?.getString("id") ?: "Null"
                    val driverViewModel: DriverScreenViewModel = hiltViewModel()
                    DriverScreen(viewModel = driverViewModel, driverId = driverId, onBack = { navController.popBackStack() })
                }

                // Constructor details
                composable(route = Destinations.Constructor.route, arguments = listOf(navArgument("id") { type = NavType.StringType })) { backStackEntry ->
                    val constructorId = backStackEntry.arguments?.getString("id") ?: "Null"
                    val constructorViewModel: ConstructorScreenViewModel = hiltViewModel()
                    ConstructorScreen(viewModel = constructorViewModel, constructorId = constructorId, onBack = { navController.popBackStack() })
                }

                // User profile
                composable(route = BottomNavDestinations.Profile.route) {
                    ProfileScreen(themeViewModel = themeViewModel, authViewModel = authViewModel)
                }

                // All drivers list, redirected from DriversAndTeamsScreen()
                composable(route = Destinations.Drivers.route) {
                    val allDriversViewModel: AllDriversViewModel = hiltViewModel()
                    AllDriversScreen(viewModel = allDriversViewModel, onBack = { navController.popBackStack() })
                }

                // All constructors list, redirected from DriversAndTeamsScreen()
               composable(route = Destinations.Constructors.route) {
                    val allConstructorsViewModel: AllConstructorsViewModel = hiltViewModel()
                    AllConstructorsScreen(viewModel = allConstructorsViewModel, onBack = { navController.popBackStack() })
               }
            }
        }
    }
}