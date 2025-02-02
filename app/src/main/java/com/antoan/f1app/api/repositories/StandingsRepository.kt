package com.antoan.f1app.api.repositories

import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.ConstructorStandings
import com.antoan.f1app.api.models.DriverStandings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StandingsRepository @Inject constructor(
    private val apiSingleton: ApiSingleton
) {
    suspend fun getDriverStandings(
        season: String = "current"
    ): List<DriverStandings> {
        return withContext(Dispatchers.IO) {
            apiSingleton.isInitialized.first { it }

            try {
                apiSingleton.getF1Api().getDriverStandings(season)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getConstructorStandings(
        season: String = "current"
    ): List<ConstructorStandings> {
        return withContext(Dispatchers.IO) {
            apiSingleton.isInitialized.first { it }

            try {
                apiSingleton.getF1Api().getConstructorStandings(season)
            } catch (e: Exception) {
                emptyList()
            }
        }

    }
}