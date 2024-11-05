package com.antoan.f1app.navigation

enum class Destinations(val route: String){
    Login("login"),
    Home("home"),
    Standings("standings"),
    Drivers("drivers"),
    DriverInfo("drivers/{id}"),
    Constructors("constructors"),
    ConstructorInfo("constructors/{id}"),

}