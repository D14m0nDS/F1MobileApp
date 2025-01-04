package com.antoan.f1app.api


import com.antoan.f1app.api.interceptors.AuthInterceptor
import com.antoan.f1app.api.interceptors.ErrorHandlingInterceptor
import com.antoan.f1app.api.services.BackendApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiSingleton {
    val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor {
            // Get the token
            "your_access_token_here"
        })
        .addInterceptor(ErrorHandlingInterceptor())
        .build()


    val retrofit = Retrofit.Builder()
        .baseUrl("https://your-backend-api.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val backendApi = retrofit.create(BackendApi::class.java)
}