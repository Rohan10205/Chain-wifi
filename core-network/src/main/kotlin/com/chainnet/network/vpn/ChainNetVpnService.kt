package com.chainnet.network.vpn

import android.net.VpnService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.FileOutputStream

class ChainNetVpnService : VpnService() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var vpnJob: Job? = null

    fun startTunnel(): VpnService.Builder {
        return Builder()
            .addAddress("10.0.0.1", 24)
            .addRoute("0.0.0.0", 0)
            .setSession("ChainNet")
    }

    fun startForwarding() {
        val tun = startTunnel().establish() ?: return
        vpnJob = scope.launch {
            val input = FileInputStream(tun.fileDescriptor)
            val output = FileOutputStream(tun.fileDescriptor)
            val buffer = ByteArray(1500)
            while (true) {
                val read = input.read(buffer)
                if (read <= 0) break
                output.write(buffer, 0, read)
            }
        }
    }

    fun stopForwarding() {
        vpnJob?.cancel()
        vpnJob = null
    }
}
