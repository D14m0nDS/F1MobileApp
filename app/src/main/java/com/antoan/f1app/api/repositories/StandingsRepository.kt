package com.antoan.f1app.api.repositories

import com.antoan.f1app.api.models.Constructor
import com.antoan.f1app.api.models.Driver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StandingsRepository @Inject constructor(
) {
    suspend fun getDriverStandings(): List<Driver> {
        return withContext(Dispatchers.IO) {
            try {
                    emptyList<Driver>()
//                listOf(Driver(
//                    name = "Lando Norris",
//                    points = 100,
//                    constructor = "McLaren",
//                    id = 1,
//                    position = 2,
//                    podiums = 5,
//                    raceResults = emptyMap(),
//                    driverNumber = 4,
//                    nationality = TODO(),
//                    dateOfBirth = TODO(),
//                    wins = TODO()
//                ), Driver(name = "Max Verstappen", points = 200, constructor = "Red Bull Racing"))
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getConstructorStandings(): List<Constructor> {
        return withContext(Dispatchers.IO) {
            try {
                emptyList<Constructor>()
//                listOf(Constructor(name = "McLaren", points = 100), Constructor(name = "Red Bull Racing", points = 200))
            } catch (e: Exception) {
                emptyList()
            }
        }

    }
}