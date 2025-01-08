package com.antoan.f1app.api.repositories

import com.antoan.f1app.api.models.ConstructorStanding
import com.antoan.f1app.api.models.DriverStanding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StandingsRepository @Inject constructor(
) {
    suspend fun getDriverStandings(): List<DriverStanding> {
        return withContext(Dispatchers.IO) {
            try {
                listOf(DriverStanding(name = "Lando Norris", points = 100, constructor = "McLaren"), DriverStanding(name = "Max Verstappen", points = 200, constructor = "Red Bull Racing"))
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getConstructorStandings(): List<ConstructorStanding> {
        return withContext(Dispatchers.IO) {
            try {
                listOf(ConstructorStanding(name = "McLaren", points = 100), ConstructorStanding(name = "Red Bull Racing", points = 200))
            } catch (e: Exception) {
                emptyList()
            }
        }

    }
}