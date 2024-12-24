package com.antoan.f1app.navigation

enum class Destinations(val route: String){
    Login("login"),
    Home("home"),
    Standings("standings"),
    Drivers("drivers"),
    Driver("drivers/{id}"),
    Constructors("constructors"),
    Constructor("constructors/{id}")
}