package com.antoan.f1app.api.models

data class DriverStandings(
    val driverId: String,
    val name: String,
    val constructorId: String,
    val constructorName: String,
    val points: Float,
    val position: Int,
    val wins: Int,
    val number: Int
)
