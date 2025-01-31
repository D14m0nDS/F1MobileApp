package com.antoan.f1app.api.services

import com.antoan.f1app.api.models.AuthResponse
import com.antoan.f1app.api.models.LoginRequest
import com.antoan.f1app.api.models.RefreshTokenRequest
import com.antoan.f1app.api.models.RegistrationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body credentials: LoginRequest): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(@Body registration: RegistrationRequest): Response<AuthResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshToken: RefreshTokenRequest): Response<AuthResponse>

}
