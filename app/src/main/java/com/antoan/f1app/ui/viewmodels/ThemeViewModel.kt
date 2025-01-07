package com.antoan.f1app.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// View model to track the selected theme from user
@HiltViewModel
class ThemeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val isDarkTheme = mutableStateOf(sharedPreferences.getBoolean("isDarkTheme", false))

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
        saveThemePreference(isDarkTheme.value)
    }

    private fun saveThemePreference(isDark: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPreferences.edit().putBoolean("isDarkTheme", isDark).apply()
        }
    }
}
