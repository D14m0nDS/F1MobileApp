package com.antoan.f1app.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.antoan.f1app.ui.screens.ConstructorScreen
import com.antoan.f1app.ui.screens.DriverScreen
import com.antoan.f1app.ui.screens.HomeScreen
import com.antoan.f1app.ui.screens.LoginScreen
import com.antoan.f1app.ui.screens.StandingsScreen
import com.antoan.f1app.viewmodels.LoginViewModel


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
) {
    val isLoggedIn = loginViewModel.isLoggedIn()
    val startDestination = if (isLoggedIn) Destinations.Home.route else Destinations.Home.route
    NavHost(navController, startDestination = startDestination, modifier = modifier) {
        composable(route = Destinations.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Destinations.Home.route)
            })
        }
        composable(
            route = Destinations.Home.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }) {
            HomeScreen()
        }
        composable(route = Destinations.Drivers.route) {
            DriverScreen()
        }
        composable(route = Destinations.Constructors.route) {
            ConstructorScreen()
        }
        composable(route = Destinations.Standings.route) {
            StandingsScreen()
        }
    }
}