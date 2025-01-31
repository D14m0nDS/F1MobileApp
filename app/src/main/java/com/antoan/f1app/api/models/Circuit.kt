package com.antoan.f1app.api.models

import com.google.gson.annotations.SerializedName

data class Circuit(
    @SerializedName("circuitName") val name: String,
    @SerializedName("Location") val location: Location

) {
    constructor() : this("", Location())
}