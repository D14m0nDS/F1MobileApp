package com.antoan.f1app.api.models

import com.google.gson.annotations.SerializedName

data class ConstructorStandings(
    @SerializedName("constructor_id")
    val constructorId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("points")
    val points: Float,
    @SerializedName("position")
    val position: Int,
    @SerializedName("wins")
    val wins: Int
)
