package com.chainnet.domain.model

data class NodeCapabilities(
    val supportsApSta: Boolean,
    val supportsWpa3: Boolean,
    val batteryPercent: Int,
    val signalLevel: Int
)
