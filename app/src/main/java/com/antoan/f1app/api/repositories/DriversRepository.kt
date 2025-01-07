package com.antoan.f1app.api.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DriversRepository @Inject constructor() {

    suspend fun getAllDrivers(): List<String> {
        return withContext(Dispatchers.IO) {
            // Replace with actual API call when backend is ready
            try {
                // Simulated API call
                listOf("Lando Norris", "Max Verstappen", "Lewis Hamilton", "George Russel")
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}