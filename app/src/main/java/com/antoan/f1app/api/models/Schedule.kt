package com.antoan.f1app.api.models

import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("season")
    val season: Int,
    @SerializedName("races")
    val races: List<Race> = emptyList()
)
