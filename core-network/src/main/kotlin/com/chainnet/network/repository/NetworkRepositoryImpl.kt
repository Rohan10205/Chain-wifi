package com.chainnet.network.repository

import com.chainnet.domain.model.ChainState
import com.chainnet.domain.model.NetworkRole
import com.chainnet.domain.repository.NetworkRepository
import com.chainnet.network.wifi.SoftApManager
import com.chainnet.network.wifi.StaConnectionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NetworkRepositoryImpl(
    private val softApManager: SoftApManager,
    private val staConnectionManager: StaConnectionManager
) : NetworkRepository {
    private val _state = MutableStateFlow(
        ChainState(
            role = NetworkRole.EDGE,
            hopCount = 0,
            isUpstreamConnected = false,
            isHotspotActive = false
        )
    )

    override fun observeChainState(): StateFlow<ChainState> = _state

    override suspend fun startChain() {
        val hotspotInfo = softApManager.startHotspot()
        _state.value = _state.value.copy(isHotspotActive = hotspotInfo.ssid.isNotBlank())
    }

    override suspend fun stopChain() {
        softApManager.stopHotspot()
        _state.value = _state.value.copy(isHotspotActive = false, isUpstreamConnected = false)
    }

    override suspend fun connectUpstream(ssid: String, passphrase: String) {
        staConnectionManager.connect(ssid, passphrase)
        _state.value = _state.value.copy(isUpstreamConnected = true)
    }
}
