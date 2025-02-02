package com.antoan.f1app.api.models

import com.google.gson.annotations.SerializedName

data class Constructor(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("drivers")
    val driverIds: List<String>,
    @SerializedName("points")
    val points: Float,
) {
    constructor() : this("", "", "", emptyList(), 0f)
}