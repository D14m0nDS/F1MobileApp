package com.antoan.f1app.ui.viewmodels

import android.content.Context

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val preferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    private val _isLoggedIn = mutableStateOf(preferences.getBoolean("isLoggedIn", false))
    val isLoggedIn: State<Boolean> = _isLoggedIn

    fun setLoggedIn(loggedIn: Boolean) {
        preferences.edit().putBoolean("isLoggedIn", loggedIn).apply()

        _isLoggedIn.value = loggedIn
    }
}
