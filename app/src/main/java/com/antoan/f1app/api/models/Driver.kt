package com.antoan.f1app.api.models

data class Driver(
    val id: Int,
    val name: String,
    val points: Int,
    val position: Int,
    val raceResults: Map<String, Int>,
    val constructor: String,
    val nationality: String,
    val dateOfBirth: String,
    val wins: Int,
    val podiums: Int,
    val driverNumber: Int
)