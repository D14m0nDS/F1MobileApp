package com.antoan.f1app.api.interceptors

import android.util.Log
import com.antoan.f1app.api.models.RefreshTokenRequest
import com.antoan.f1app.api.services.AuthApi
import com.antoan.f1app.network.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import kotlinx.coroutines.runBlocking


class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
    private val authApi: AuthApi
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Skip authorization for login and registration endpoints
        val path = request.url.encodedPath
        if (path.startsWith("/auth/")) {
            Log.d("AuthInterceptor", "Skipping Authorization for: $path")
            return chain.proceed(request)
        }

        val accessToken = tokenManager.accessToken ?: return chain.proceed(request)

        val authenticatedRequest = request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(authenticatedRequest)

        // Handle token expiration (401 Unauthorized)
        if (response.code == 401) {
            synchronized(this) {
                val newToken = runBlocking { refreshToken(tokenManager.refreshToken) } // ✅ Use runBlocking
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

    private suspend fun refreshToken(refreshToken: String?): String? {
        if (refreshToken == null) return null
        return try {
            val response = authApi.refreshToken(RefreshTokenRequest(refreshToken))

            if (response.isSuccessful) {
                response.body()?.accessToken
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun logout() {
        val refreshToken = tokenManager.refreshToken

        if (refreshToken != null) {
            try {
                authApi.logout("Bearer $refreshToken")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        tokenManager.clearTokens()
    }
}