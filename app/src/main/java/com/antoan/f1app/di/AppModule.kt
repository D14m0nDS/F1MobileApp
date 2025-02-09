package com.antoan.f1app.di

import android.content.Context
import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.interceptors.AuthInterceptor
import com.antoan.f1app.api.services.AuthApi
import com.antoan.f1app.network.BroadcastListener
import com.antoan.f1app.network.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        tokenManager: TokenManager,
        authApi: AuthApi
    ): AuthInterceptor {
        return AuthInterceptor(tokenManager, authApi)
    }

    @Provides
    @Singleton
    fun provideApiSingleton(authInterceptor: dagger.Lazy<AuthInterceptor>): ApiSingleton {
        return ApiSingleton(authInterceptor)
    }

    @Provides
    @Singleton
    fun provideBroadcastListener(apiSingleton: ApiSingleton): BroadcastListener {
        return BroadcastListener(apiSingleton)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:5000/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
