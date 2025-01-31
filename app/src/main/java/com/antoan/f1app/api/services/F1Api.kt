package com.antoan.f1app.api.services

import com.antoan.f1app.api.models.Constructor
import com.antoan.f1app.api.models.Driver
import com.antoan.f1app.api.models.Race
import com.antoan.f1app.api.models.Schedule
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface F1Api {
    @GET("f1/standings/{type}")
    suspend fun getStandings(
        @Path("type") type: String,
        @Query("season") season: String
    ): List<Any>

    @GET("f1/schedule")
    suspend fun getSchedule(): Schedule

    @GET("f1/race")
    suspend fun getRace(
        @Query("season") season: String,
        @Query("round") round: Int
    ): Race

    @GET("f1/driver")
    suspend fun getDriverInfo(
        @Query("id") id: String
    ): Driver

    @GET("f1/constructor")
    suspend fun getConstructorInfo(
        @Query("id") id: String
    ): Constructor

    @GET("f1/drivers")
    suspend fun getAllDrivers(
        @Query("season") season: String
    ): List<Driver>

    @GET("f1/constructors")
    suspend fun getAllConstructors(
        @Query("season") season: String
    ): List<Constructor>
}