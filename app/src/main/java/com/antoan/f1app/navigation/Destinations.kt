package com.antoan.f1app.navigation

// All destinations in the app
enum class Destinations(val route: String){
    Login("login"),
    Register("register"),
    Home("home"),
    Standings("standings"),
    Drivers("drivers"),
    Driver("drivers/{id}"),
    Constructors("constructors"),
    Constructor("constructors/{id}"),
    DriversAndTeams("drivers_and_teams"),
    Profile("profile"),
    Race("race/{season}/{round}");

    fun createRaceRoute(season: String, round: Int): String {
        return "race/$season/$round"
    }
}