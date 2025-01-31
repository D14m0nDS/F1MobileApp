package com.antoan.f1app.api.models

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("password_confirmation")
    val passwordConfirmation: String
)