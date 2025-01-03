package com.antoan.f1app.api.repositories

import com.antoan.f1app.api.services.BackendApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConstructorsRepository(private val api: BackendApi) {
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