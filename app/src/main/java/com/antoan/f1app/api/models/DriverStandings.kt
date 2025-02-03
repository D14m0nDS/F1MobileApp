package com.antoan.f1app.api.models

import com.google.gson.annotations.SerializedName

data class DriverStandings(
    @SerializedName("driver_id")
    val driverId: String,
    @SerializedName("driver_name")
    val name: String,
    @SerializedName("constructor_id")
    val constructorId: String,
    @SerializedName("constructor_name")
    val constructorName: String,
    @SerializedName("points")
    val points: Float,
    @SerializedName("position")
    val position: Int,
    @SerializedName("wins")
    val wins: Int,
    @SerializedName("driver_number")
    val number: Int
)
