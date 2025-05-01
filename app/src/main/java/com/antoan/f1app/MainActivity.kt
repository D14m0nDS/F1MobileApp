package com.antoan.f1app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.navigation.AppNavigation
import com.antoan.f1app.ui.components.LoadingScreen
import com.antoan.f1app.ui.theme.F1AppTheme
import com.antoan.f1app.ui.viewmodels.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var apiSingleton: ApiSingleton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Start with loading screen
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val isInitialized by apiSingleton.isInitialized.collectAsState(initial = false)

            LaunchedEffect(Unit) {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    apiSingleton.initialize("https://flask-backend-252148344516.europe-west3.run.app")
                }
            }

            F1AppTheme(darkTheme = themeViewModel.isDarkTheme.value, dynamicColor = false) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (isInitialized) {
                        AppNavigation()
                    } else {
                        LoadingScreen()
                    }
                }
            }
        }
    }
}
