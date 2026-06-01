package com.chainnet.domain.model

data class Route(
    val destinationNodeId: String,
    val nextHopNodeId: String,
    val hopCount: Int,
    val expiresAtEpochMillis: Long
)
