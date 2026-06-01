package com.chainnet.network.wifi

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiNetworkSpecifier
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class StaConnectionManager(private val connectivityManager: ConnectivityManager) {
    suspend fun connect(ssid: String, passphrase: String): Network =
        suspendCancellableCoroutine { continuation ->
            val specifier = WifiNetworkSpecifier.Builder()
                .setSsid(ssid)
                .setWpa2Passphrase(passphrase)
                .build()

            val request = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .setNetworkSpecifier(specifier)
                .build()

            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    continuation.resume(network)
                }

                override fun onUnavailable() {
                    continuation.resumeWithException(IllegalStateException("Upstream network unavailable"))
                }
            }

            connectivityManager.requestNetwork(request, callback)
            continuation.invokeOnCancellation {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }
}
