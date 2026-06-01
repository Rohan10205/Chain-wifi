package com.chainnet.domain.model

data class PeerNode(
    val identity: NodeIdentity,
    val ipAddress: String,
    val hopCount: Int,
    val role: NetworkRole,
    val lastSeenEpochMillis: Long,
    val capabilities: NodeCapabilities
)
