package com.chainnet.service

import android.os.PowerManager
import androidx.lifecycle.LifecycleService
import com.chainnet.domain.repository.NetworkRepository
import com.chainnet.network.control.ControlPlaneServer
import com.chainnet.network.discovery.NsdDiscovery
import com.chainnet.network.discovery.WifiP2pDiscovery
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChainNetService : LifecycleService() {
    @Inject lateinit var networkRepository: NetworkRepository
    @Inject lateinit var controlPlaneServer: ControlPlaneServer
    @Inject lateinit var nsdDiscovery: NsdDiscovery
    @Inject lateinit var wifiP2pDiscovery: WifiP2pDiscovery

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var wakeLock: PowerManager.WakeLock? = null

    override fun onCreate() {
        super.onCreate()
        val helper = NotificationHelper(this)
        helper.createChannel()
        startForeground(1, helper.buildForegroundNotification())

        val powerManager = getSystemService(PowerManager::class.java)
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ChainNet::Service")
        wakeLock?.acquire()

        controlPlaneServer.start()
        nsdDiscovery.discoverServices()
        wifiP2pDiscovery.startDiscovery()

        scope.launch {
            networkRepository.startChain()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        wifiP2pDiscovery.stopDiscovery()
        nsdDiscovery.stopDiscovery()
        controlPlaneServer.stop()
        scope.launch {
            networkRepository.stopChain()
        }
        wakeLock?.release()
    }
}
