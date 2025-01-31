package com.antoan.f1app.api.models

data class ConstructorStandings(
    val constructorId: String,
    val name: String,
    val points: Float,
    val position: Int,
    val wins: Int
)
