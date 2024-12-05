package com.antoan.f1app.api


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class apiSingleton {
    private val client = OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).build()

    val retrofit = Retrofit.Builder()
        .baseUrl("")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val backendApi = retrofit.create(BackendApi::class.java)
}