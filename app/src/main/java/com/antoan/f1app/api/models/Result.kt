package com.antoan.f1app.api.models

data class Result(
    val raceId: String,
    val position: Float,
    val driverId: String,
    val driverName: String,
    val constructorId: String,
    val constructorName: String,
    val time: String,
    val points: Float,
    val status: String
)
