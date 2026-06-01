package com.chainnet.domain.model

data class TopologyGraph(
    val nodes: List<PeerNode>,
    val edges: List<TopologyEdge>
)
