package com.antoan.f1app.navigation

// All destinations in the app
enum class Destinations(val route: String){
    Login("login"),
    Home("home"),
    Standings("standings"),
    Drivers("drivers"),
    Driver("drivers/{id}"),
    Constructors("constructors"),
    Constructor("constructors/{id}"),
    DriversAndTeams("drivers_and_teams"),
    Profile("profile")
}