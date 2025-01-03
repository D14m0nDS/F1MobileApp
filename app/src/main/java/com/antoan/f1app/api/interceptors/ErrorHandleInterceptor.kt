package com.antoan.f1app.api.interceptors

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

// Interceptor for possible errors in the responses from the backend
class ErrorHandlingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        when (response.code()) {
            401 -> {
                // Unauthorized
                throw IOException("Unauthorized - You may need to log in again.")
            }
            500 -> {
                // Internal Server Error
                throw IOException("Server is currently unavailable. Please try again later.")
            }
        }

        return response
    }
}