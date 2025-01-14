package com.antoan.f1app.api.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ConstructorsRepository @Inject constructor(
) {
    suspend fun getAllConstructors(): List<String> {
        return withContext(Dispatchers.IO) {
            // Replace with actual API call when backend is ready
            try {
                // Simulated API call
                listOf("Red Bull Racing", "Mercedes AMG", "Ferrari", "McLaren")
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}