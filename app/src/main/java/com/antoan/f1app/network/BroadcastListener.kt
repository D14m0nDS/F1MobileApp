package com.antoan.f1app.network

import android.util.Log
import com.antoan.f1app.api.ApiSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import javax.inject.Inject

class BroadcastListener @Inject constructor(
    private val apiSingleton: ApiSingleton
) {
    fun start(callback: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val socket = DatagramSocket(5000).apply { broadcast = true }
            val buffer = ByteArray(1024)
            while (true) {
                try {
                    val packet = DatagramPacket(buffer, buffer.size)
                    socket.receive(packet)
                    val message = String(packet.data, 0, packet.length)
                    if (message.startsWith("flask_backend:")) {
                        val ip = message.split(":")[1]
                        callback(ip)

                        val baseUrl = "http://$ip:5000/"
                        apiSingleton.initialize(baseUrl)

                        break
                    }
                } catch (e: Exception) {
                    Log.e("Broadcast", "Error: ${e.message}")
                    break
                }
            }
            socket.close()
        }
    }
}
