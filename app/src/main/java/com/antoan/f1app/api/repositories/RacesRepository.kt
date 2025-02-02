package com.antoan.f1app.api.repositories

import android.util.Log
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
    suspend fun getSchedule(): Schedule {
        return withContext(Dispatchers.IO) {
            apiSingleton.isInitialized.first { it }

            try {
                apiSingleton.getF1Api().getSchedule().schedule
            } catch (e: Exception) {
                Schedule(0)
            }
        }
    }
    suspend fun getRace(
        season: String,
        round: Int
    ): Race {
        return withContext(Dispatchers.IO) {
            apiSingleton.isInitialized.first { it }

            try {
                apiSingleton.getF1Api().getRace(season, round)
            } catch (e: Exception) {
                Race()
            }
        }
    }
}