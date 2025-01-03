package com.antoan.f1app.ui.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = application.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean("isLoggedIn", false)
    }

    fun setLoggedIn(loggedIn: Boolean) {
        preferences.edit().putBoolean("isLoggedIn", loggedIn).apply()
    }
}
