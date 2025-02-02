package com.antoan.f1app.api.models

import com.google.gson.annotations.SerializedName

data class Driver(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("constructor_id")
    val constructorId: String,
    @SerializedName("constructor_name")
    val constructorName: String,
    @SerializedName("points")
    val points: Float,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("headshot_url")
    val headshotUrl: String,
) {
    constructor() : this("", "", 0, 0, "", "", "", 0f, emptyList(), "")
}