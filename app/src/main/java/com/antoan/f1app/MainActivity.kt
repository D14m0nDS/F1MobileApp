package com.antoan.f1app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.antoan.f1app.api.apiSingleton
import com.antoan.f1app.navigation.AppNavigation
import com.antoan.f1app.ui.theme.F1AppTheme
import com.antoan.f1app.ui.viewmodels.ThemeViewModel
import com.antoan.f1app.ui.viewmodels.factory.GenericViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val api = apiSingleton().backendApi
            val themeViewModel: ThemeViewModel = viewModel(factory = GenericViewModelFactory {
                ThemeViewModel()
            })

            F1AppTheme(
                darkTheme = themeViewModel.isDarkTheme.value
            ) {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        themeViewModel = themeViewModel,
                        api = api
                    )
                }
            }
        }
    }
}