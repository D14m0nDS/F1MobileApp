package com.antoan.f1app.api.models

import com.google.gson.annotations.SerializedName

data class ConstructorInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("drivers")
    val drivers: List<ConstructorInfoDriver>,
    @SerializedName("points")
    val points: Float,
    @SerializedName("race_results")
    val raceResults: List<ConstructorInfoRaceResult>,
) {
    constructor() : this("", "", "", emptyList(), 0f, emptyList())
}

data class ConstructorInfoDriver(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
)

data class ConstructorInfoRaceResult(
    @SerializedName("race_id")
    val raceId: String,
    @SerializedName("round")
    val round: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("total_points")
    val totalPoints: Float,
    @SerializedName("driver_results")
    val driverResults: List<ConstructorInfoDriverResult>,
)

data class ConstructorInfoDriverResult(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("position")
    val position: Float,
    @SerializedName("points")
    val points: Float,
    @SerializedName("time")
    val time: String?,
    @SerializedName("status")
    val status: String,
)
