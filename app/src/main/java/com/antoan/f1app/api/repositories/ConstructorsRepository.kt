package com.antoan.f1app.api.repositories

import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.Constructor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ConstructorsRepository @Inject constructor(
    private val apiSingleton: ApiSingleton
) {
    suspend fun getAllConstructors(
        season: String = "current"
    ): List<Constructor> {
        return withContext(Dispatchers.IO) {
            apiSingleton.isInitialized.first { it }

            try {
                apiSingleton.getF1Api().getAllConstructors(season)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getConstructor(id: String): Constructor {
        return withContext(Dispatchers.IO) {
            apiSingleton.isInitialized.first { it }

            try {
                apiSingleton.getF1Api().getConstructorInfo(id)
            } catch (e: Exception) {
                Constructor()
            }
        }
    }
}