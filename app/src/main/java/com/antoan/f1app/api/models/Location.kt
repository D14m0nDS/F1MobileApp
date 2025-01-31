package com.antoan.f1app.api.models

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city") val city: String,
    @SerializedName("country") val country: String


) {
    constructor() : this("", "")
}