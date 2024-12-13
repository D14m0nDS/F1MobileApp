package com.antoan.f1app.api

import com.antoan.f1app.api.models.Driver
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BackendApi {
    @GET("standings/{type}")
    fun getDrivers(
        @Path("type") type: String,
    ): Call<List<Any>>

    @GET("driverInfo/{id}")
    fun getDriverInfo(
        @Path("id") id: Int,
    ): Call<Driver>
}