package com.chainnet.network.wifi

data class HotspotInfo(
    val ssid: String,
    val passphrase: String,
    val band: Int,
    val bssid: String?
)
