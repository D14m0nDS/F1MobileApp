package com.antoan.f1app.api.models

data class Constructor(
    val id: String,
    val name: String,
    val nationality: String,
    val driverIds: List<String>,
    val points: Float,
)