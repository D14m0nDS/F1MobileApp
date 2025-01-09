package com.antoan.f1app.api.repositories

import com.antoan.f1app.api.models.Race
import com.antoan.f1app.api.models.RaceInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RacesRepository @Inject constructor(
){
    suspend fun getAllRaces(): List<Race> {
        return withContext(Dispatchers.IO) {
            try {
                listOf(Race("Silverstone"), Race("Miami"), Race("Monaco"))
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
    suspend fun getRaceInfo(): RaceInfo {
        return withContext(Dispatchers.IO) {
            try {
                RaceInfo("Monaco")
            } catch (e: Exception) {
                RaceInfo("")
            }
        }
    }
}