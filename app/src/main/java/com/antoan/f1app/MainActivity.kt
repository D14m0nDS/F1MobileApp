package com.antoan.f1app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.interceptors.AuthInterceptor
import com.antoan.f1app.navigation.AppNavigation
import com.antoan.f1app.network.BroadcastListener
import com.antoan.f1app.ui.components.LoadingScreen
import com.antoan.f1app.ui.theme.F1AppTheme
import com.antoan.f1app.ui.viewmodels.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var apiSingleton: ApiSingleton
    private val broadcastListener = BroadcastListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Start with loading screen
        setContent { LoadingScreen() }

        broadcastListener.start { ip ->
            lifecycleScope.launch {  // Changed to coroutine
                val baseUrl = "http://$ip:5000/"
                apiSingleton.initialize(baseUrl)
                apiSingleton.getAuthInterceptor().setBaseUrl(baseUrl)
            }
        }

        lifecycleScope.launch {
            apiSingleton.isInitialized.collect { isReady ->
                if (isReady) {
                    setContent {
                        val themeViewModel: ThemeViewModel = hiltViewModel()
                        F1AppTheme(themeViewModel.isDarkTheme.value) {
                            Surface(Modifier.fillMaxSize()) {
                                AppNavigation()
                            }
                        }
                    }
                }
            }
        }
    }
}
