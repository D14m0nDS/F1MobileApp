package com.antoan.f1app.api.repositories

import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.Race
import com.antoan.f1app.api.models.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RacesRepository @Inject constructor(
    private val apiSingleton: ApiSingleton
){
    suspend fun getAllRaces(): Schedule {
        return withContext(Dispatchers.IO) {
            apiSingleton.isInitialized.first { it }

            try {
                apiSingleton.getF1Api().getSchedule()
            } catch (e: Exception) {
                Schedule(0, emptyList())
            }
        }
    }
    suspend fun getRaceInfo(): Race {
        return withContext(Dispatchers.IO) {
            try {
                apiSingleton.getF1Api().getRace()
            } catch (e: Exception) {
                Race("")
            }
        }
    }
}