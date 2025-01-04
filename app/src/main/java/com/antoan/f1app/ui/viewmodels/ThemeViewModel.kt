package com.antoan.f1app.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// View model to track the selected theme from user
class ThemeViewModel(context: Context) : ViewModel() {
    private val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val isDarkTheme = mutableStateOf(sharedPreferences.getBoolean("isDarkTheme", false))

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
        sharedPreferences.edit().putBoolean("isDarkTheme", isDarkTheme.value).apply()
    }
}
