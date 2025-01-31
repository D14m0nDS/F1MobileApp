package com.antoan.f1app.api.models

data class Schedule(
    val season: Int,
    val races: List<Race>
)
