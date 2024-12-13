package com.antoan.f1app.api.models

data class Constructor(
    val id: Int,
    val name: String,
    val points: Int,
    val position: Int,
    val drivers: List<Driver>,
    val wins: Int,
    val podiums: Int,
    val base: String,
    val raceResults: Map<String, Pair<Int, Int>>,
)