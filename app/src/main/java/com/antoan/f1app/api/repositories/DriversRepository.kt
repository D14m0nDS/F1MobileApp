package com.antoan.f1app.api.repositories

import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.Driver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DriversRepository @Inject constructor(
    private val apiSingleton: ApiSingleton
) {

    suspend fun getAllDrivers(
        season: String = "current"
    ): List<Driver> {
        return withContext(Dispatchers.IO) {
            apiSingleton.isInitialized.first { it }

            try {
                apiSingleton.getF1Api().getAllDrivers(season)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getDriver(id: String): Driver {
        return withContext(Dispatchers.IO) {
            apiSingleton.isInitialized.first { it }

            try {
                apiSingleton.getF1Api().getDriverInfo(id)
            } catch (e: Exception) {
                Driver()
            }
        }
    }
}