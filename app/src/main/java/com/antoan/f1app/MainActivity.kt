package com.antoan.f1app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.core.view.WindowCompat
import com.antoan.f1app.navigation.AppNavigation
import com.antoan.f1app.ui.theme.F1AppTheme
import com.antoan.f1app.ui.viewmodels.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import java.net.DatagramPacket
import java.net.DatagramSocket
import okhttp3.Request

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        WindowCompat.setDecorFitsSystemWindows(window, false)

        CoroutineScope(Dispatchers.IO).launch {
            val serverIp = listenForBroadcast()
            Log.d("Broadcast Listener", "Server IP: $serverIp")
        }

        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()

            F1AppTheme(
                darkTheme = themeViewModel.isDarkTheme.value
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }

    private fun listenForBroadcast(): String? {
        Log.d("Broadcast Listener", "Starting to listen for broadcasts...")
        val buffer = ByteArray(1024)
        val socket = DatagramSocket(5000) // Listen on the same port as the backend broadcasts
        socket.broadcast = true
        val packet = DatagramPacket(buffer, buffer.size)
        Log.d("Broadcast Listener", "Starting to listen for broadcasts2")

        return try {
            socket.receive(packet)
            Log.d("Broadcast Listener", "Received a packet")
            val receivedData = String(packet.data, 0, packet.length)
            Log.d("Broadcast Listener", "Data: $receivedData")

            if (receivedData.startsWith("flask_backend:")) {
                val serverIp = receivedData.split(":")[1] // Extract the IP address
                // Perform a test request to the `/f1/schedule` endpoint
                testRequestToServer(serverIp)
                serverIp
            } else null
        } catch (e: Exception) {
            Log.e("Broadcast Listener", "Error: ${e.message}")
            e.printStackTrace()
            null
        } finally {
            socket.close()
            Log.d("Broadcast Listener", "Socket closed")
        }
    }

    private fun testRequestToServer(ip: String) {
        // Create an HTTP client
        val client = OkHttpClient()

        // Construct the URL for the test request
        val url = "http://$ip:5000/f1/schedule"

        // Build the request
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        // Perform the request on a background thread
        CoroutineScope(Dispatchers.IO).launch {
            try {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body()?.string() // Use `body?.string()` to retrieve the content
                        Log.d("Test Request", "Response from $url: $responseBody")
                    } else {
                        Log.e("Test Request", "Request failed with code: ${response.code()}") // Use `response.code`
                    }
                }
            } catch (e: Exception) {
                Log.e("Test Request", "Error making test request: ${e.message}")
                e.printStackTrace()
            }
        }
    }


}