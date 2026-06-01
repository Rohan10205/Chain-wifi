package com.chainnet.domain.model

data class ChainState(
    val role: NetworkRole,
    val hopCount: Int,
    val isUpstreamConnected: Boolean,
    val isHotspotActive: Boolean
)
