package com.antoan.f1app.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.AuthResponse
import com.antoan.f1app.api.models.LoginRequest
import com.antoan.f1app.api.models.RegistrationRequest
import com.antoan.f1app.network.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val api: ApiSingleton,
    private val tokenManager: TokenManager
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var username by mutableStateOf("")
    var passwordConfirmation by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    val isLoggedIn = mutableStateOf(tokenManager.accessToken != null)

    fun login() {
        viewModelScope.launch {
            try {
                isLoading = true
                error = null
                val response = api.getAuthApi().login(
                    LoginRequest(
                        email = email.trim(),
                        password = password.trim()
                    )
                )
                handleAuthResponse(response)
            } catch (e: Exception) {
                error = "Network error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun register() {
        viewModelScope.launch {
            try {
                if (password != passwordConfirmation) {
                    error = "Passwords do not match"
                    return@launch
                }

                isLoading = true
                error = null
                val response = api.getAuthApi().register(
                    RegistrationRequest(
                        username = username.trim(),
                        email = email.trim().lowercase(),
                        password = password.trim(),
                        passwordConfirmation = passwordConfirmation.trim()
                    )
                )
                handleAuthResponse(response)
            } catch (e: Exception) {
                error = "Registration failed: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun handleAuthResponse(response: Response<AuthResponse>) {
        if (response.isSuccessful) {
            response.body()?.let {
                Log.d("AuthViewModel", "Access Token: ${it.accessToken}")
                Log.d("AuthViewModel", "Refresh Token: ${it.refreshToken}")
                tokenManager.accessToken = it.accessToken
                tokenManager.refreshToken = it.refreshToken
                isLoggedIn.value = true
            } ?: run {
                error = "Invalid server response"
            }
        } else {
            error = when (response.code()) {
                400 -> "Invalid request format"
                401 -> "Invalid credentials"
                429 -> "Too many attempts - try again later"
                else -> "Authentication failed (${response.code()})"
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                val accessToken = tokenManager.accessToken
                val refreshToken = tokenManager.refreshToken
                Log.d("AuthViewModel", "Access Token: $accessToken")
                Log.d("AuthViewModel", "Refresh Token: $refreshToken")
                if (accessToken != null) {
                    api.getAuthApi().logout("Bearer ${accessToken.trim()}")
                }


                if (refreshToken != null) {
                    val formattedToken = "Bearer ${refreshToken.trim()}"
                    Log.d("AuthViewModel", "Sending Refresh Token: $formattedToken")
                    api.getAuthApi().logoutRefresh(formattedToken)
                }
            } catch (e: Exception) {
                error = "Logout failed: ${e.message}"
            } finally {
                tokenManager.clearTokens()
                isLoggedIn.value = false
                email = ""
                password = ""
            }
        }
    }
}