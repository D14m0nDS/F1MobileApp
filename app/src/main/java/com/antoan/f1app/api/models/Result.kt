package com.antoan.f1app.api.models

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("race_id")
    val raceId: String,
    @SerializedName("position")
    val position: Float,
    @SerializedName("driver_id")
    val driverId: String,
    @SerializedName("driver_name")
    val driverName: String,
    @SerializedName("constructor_id")
    val constructorId: String,
    @SerializedName("constructor_name")
    val constructorName: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("points")
    val points: Float,
    @SerializedName("status")
    val status: String
)
