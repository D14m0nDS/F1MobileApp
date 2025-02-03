package com.antoan.f1app.api

import com.antoan.f1app.api.interceptors.AuthInterceptor
import com.antoan.f1app.api.services.AuthApi
import com.antoan.f1app.api.services.F1Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiSingleton @Inject constructor(
    private val authInterceptor: dagger.Lazy<AuthInterceptor>
) {
    private var retrofit: Retrofit? = null
    private val mutex = Mutex()
    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    suspend fun initialize(baseUrl: String) {
        mutex.withLock {
            if (retrofit == null) {
                retrofit = createRetrofit(baseUrl)
                _isInitialized.value = true
            }
        }
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        authInterceptor.get().intercept(chain)
                    }
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getF1Api(): F1Api = retrofit!!.create(F1Api::class.java)
    fun getAuthApi(): AuthApi = retrofit!!.create(AuthApi::class.java)
    fun getBaseUrl(): String = retrofit!!.baseUrl().toString()



    fun getAuthInterceptor(): AuthInterceptor = authInterceptor.get()
}