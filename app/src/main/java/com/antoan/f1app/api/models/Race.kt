package com.antoan.f1app.api.models

import com.google.gson.annotations.SerializedName

data class Race (
    @SerializedName("id")
    val id: String,
    @SerializedName("season")
    val season: Int,
    @SerializedName("round")
    val round: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("circuit")
    val circuit: Circuit,
    @SerializedName("date")
    val date: String,
    @SerializedName("results")
    val results: List<Result>
) {
    val circuitName: String get() = circuit.name
    val city: String get() = circuit.location.city
    val country: String get() = circuit.location.country

    constructor() : this("", 0, 0, "", Circuit(), "", emptyList())
}