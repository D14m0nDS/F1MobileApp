package com.antoan.f1app.api.interceptors

import com.antoan.f1app.api.models.AuthResponse
import com.antoan.f1app.network.TokenManager
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    private var baseUrl: String? = null

    fun setBaseUrl(url: String) {
        baseUrl = url
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val path = request.url.encodedPath
        if (path.startsWith("/auth/login") || path.startsWith("/auth/register")) {
            return chain.proceed(request)
        }

        val accessToken = tokenManager.accessToken
            ?: return chain.proceed(request)

        val authenticatedRequest = request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(authenticatedRequest)

        // Handle token expiration (401 Unauthorized)
        if (response.code == 401) {
            synchronized(this) {
                val newToken = refreshToken(tokenManager.refreshToken)
                newToken?.let {
                    tokenManager.accessToken = it
                    return@intercept chain.proceed(
                        request.newBuilder()
                            .header("Authorization", "Bearer $it")
                            .build()
                    )
                }
                tokenManager.clearTokens()
            }
        }

        return response
    }

    private fun refreshToken(refreshToken: String?): String? {
        val tempClient = OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url("${baseUrl ?: return null}auth/refresh")
            .header("Authorization", "Bearer $refreshToken")
            .build()

        val response = tempClient.newCall(request).execute()
        return parseToken(response)
    }

    private fun parseToken(response: Response): String? {
        return try {
            val json = response.body?.string()
            val authResponse = Gson().fromJson(json, AuthResponse::class.java)
            authResponse?.accessToken
        } catch (e: Exception) {
            null
        }
    }
}