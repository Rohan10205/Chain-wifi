package com.chainnet.domain.repository

import com.chainnet.domain.model.PeerNode
import kotlinx.coroutines.flow.Flow

interface PeerRepository {
    fun observePeers(): Flow<List<PeerNode>>
    suspend fun upsertPeer(peer: PeerNode)
    suspend fun markPeerDisconnected(nodeId: String)
}
