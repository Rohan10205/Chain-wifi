package com.chainnet.network.discovery

import android.net.wifi.p2p.WifiP2pManager
import android.os.Looper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WifiP2pDiscovery(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel
) {
    private val _isDiscovering = MutableStateFlow(false)
    val isDiscovering: StateFlow<Boolean> = _isDiscovering

    fun startDiscovery() {
        manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                _isDiscovering.value = true
            }

            override fun onFailure(reason: Int) {
                _isDiscovering.value = false
            }
        })
    }

    fun stopDiscovery() {
        manager.stopPeerDiscovery(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                _isDiscovering.value = false
            }

            override fun onFailure(reason: Int) {
                _isDiscovering.value = false
            }
        })
    }

    companion object {
        fun create(context: android.content.Context, manager: WifiP2pManager): WifiP2pDiscovery {
            val channel = manager.initialize(context, Looper.getMainLooper(), null)
            return WifiP2pDiscovery(manager, channel)
        }
    }
}
