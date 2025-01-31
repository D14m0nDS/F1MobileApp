package com.antoan.f1app.api.models

data class Driver(
    val id: String,
    val name: String,
    val age: Int,
    val number: Int,
    val nationality: String,
    val constructorId: String,
    val constructorName: String,
    val points: Float,
    val results: List<Result>,
    val headshotUrl: String,
)