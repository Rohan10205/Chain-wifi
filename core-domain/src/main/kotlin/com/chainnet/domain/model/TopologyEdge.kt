package com.chainnet.domain.model

data class TopologyEdge(
    val fromNodeId: String,
    val toNodeId: String,
    val linkQuality: Int,
    val lastUpdatedEpochMillis: Long
)
