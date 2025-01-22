package com.antoan.f1app.api

import com.antoan.f1app.api.interceptors.AuthInterceptor
import com.antoan.f1app.api.interceptors.ErrorHandlingInterceptor
import com.antoan.f1app.api.services.BackendApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiSingleton private constructor(baseUrl: String) {

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor {
            // Get the token
            "your_access_token_here"
        })
        .addInterceptor(ErrorHandlingInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val backendApi: BackendApi = retrofit.create(BackendApi::class.java)

    companion object {
        @Volatile
        private var instance: ApiSingleton? = null

        fun initialize(baseUrl: String) {
            instance = ApiSingleton(baseUrl)
        }

        fun getInstance(): ApiSingleton {
            return instance ?: throw IllegalStateException("ApiSingleton not initialized. Call initialize() first.")
        }
    }
}
