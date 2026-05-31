package com.chainnet.network.wifi

import android.net.wifi.SoftApConfiguration
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SoftApManager(private val wifiManager: WifiManager) {
    private var reservation: WifiManager.LocalOnlyHotspotReservation? = null

    suspend fun startHotspot(): HotspotInfo = suspendCancellableCoroutine { continuation ->
        val handler = Handler(Looper.getMainLooper())
        wifiManager.startLocalOnlyHotspot(object : WifiManager.LocalOnlyHotspotCallback() {
            override fun onStarted(res: WifiManager.LocalOnlyHotspotReservation) {
                reservation = res
                val config: SoftApConfiguration = res.softApConfiguration
                continuation.resume(
                    HotspotInfo(
                        ssid = config.ssid ?: "ChainNet",
                        passphrase = config.passphrase ?: "",
                        band = config.band,
                        bssid = config.bssid?.toString()
                    )
                )
            }

            override fun onFailed(reason: Int) {
                continuation.resumeWithException(IllegalStateException("Hotspot failed: ${'$'}reason"))
            }
        }, handler)

        continuation.invokeOnCancellation {
            stopHotspot()
        }
    }

    fun stopHotspot() {
        reservation?.close()
        reservation = null
    }
}
