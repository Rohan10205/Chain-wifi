package com.chainnet.network.control

data class ControlPlaneFrame(
    val type: ControlPlaneMessageType,
    val payload: ByteArray
)
