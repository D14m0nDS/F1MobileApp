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
import org.json.JSONObject
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
            response.body()?.let { auth ->
                tokenManager.accessToken = auth.accessToken
                tokenManager.refreshToken = auth.refreshToken
                isLoggedIn.value = true
            } ?: run {
                error = "Invalid server response"
            }
        } else {
            val raw = response.errorBody()?.string().orEmpty()
            val msg = try {
                JSONObject(raw).optString("message").takeIf { it.isNotBlank() } ?: raw
            } catch (e: Exception) {
                raw
            }
            error = "Error ${response.code()}: $msg"
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                // grab the refresh token
                tokenManager.refreshToken?.let { raw ->
                    val bearer = "Bearer ${raw.trim()}"
                    val resp = api.getAuthApi().logout(bearer)
                    if (!resp.isSuccessful) {
                        // optional: grab server message
                        val body = resp.errorBody()?.string().orEmpty()
                        Log.e("AuthVM", "Logout failed ${resp.code()}: $body")
                    }
                }
            } catch (e: Exception) {
                Log.e("AuthVM", "Logout exception", e)
                error = "Logout failed: ${e.message}"
            } finally {
                // clear local state in any case
                tokenManager.clearTokens()
                isLoggedIn.value = false
                email = ""
                password = ""
            }
        }
    }
}