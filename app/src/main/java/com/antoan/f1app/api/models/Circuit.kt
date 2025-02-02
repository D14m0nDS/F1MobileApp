package com.antoan.f1app.api.models

import com.google.gson.annotations.SerializedName

data class Circuit(
    @SerializedName("circuit_name") val name: String,
    @SerializedName("location") val location: Location

) {
    constructor() : this("", Location())
}