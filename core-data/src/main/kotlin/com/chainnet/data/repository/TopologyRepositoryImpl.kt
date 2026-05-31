package com.chainnet.data.repository

import com.chainnet.data.db.dao.PeerDao
import com.chainnet.data.db.dao.TopologyEdgeDao
import com.chainnet.data.db.entity.TopologyEdgeEntity
import com.chainnet.domain.model.TopologyEdge
import com.chainnet.domain.model.TopologyGraph
import com.chainnet.domain.repository.TopologyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class TopologyRepositoryImpl(
    private val peerDao: PeerDao,
    private val topologyEdgeDao: TopologyEdgeDao
) : TopologyRepository {
    override fun observeTopology(): Flow<TopologyGraph> {
        return combine(peerDao.observePeers(), topologyEdgeDao.observeEdges()) { peers, edges ->
            TopologyGraph(
                nodes = peers.map { it.toDomain() },
                edges = edges.map { it.toDomain() }
            )
        }
    }

    override suspend fun updateTopology(graph: TopologyGraph) {
        topologyEdgeDao.clearAll()
        graph.edges.forEach { edge ->
            topologyEdgeDao.upsert(edge.toEntity())
        }
    }

    private fun com.chainnet.data.db.entity.PeerEntity.toDomain(): com.chainnet.domain.model.PeerNode {
        return com.chainnet.domain.model.PeerNode(
            identity = com.chainnet.domain.model.NodeIdentity(nodeId, publicKey, displayName),
            ipAddress = ipAddress,
            hopCount = hopCount,
            role = com.chainnet.domain.model.NetworkRole.valueOf(role),
            lastSeenEpochMillis = lastSeenEpochMillis,
            capabilities = com.chainnet.domain.model.NodeCapabilities(
                supportsApSta = supportsApSta,
                supportsWpa3 = supportsWpa3,
                batteryPercent = batteryPercent,
                signalLevel = signalLevel
            )
        )
    }

    private fun TopologyEdgeEntity.toDomain(): TopologyEdge = TopologyEdge(
        fromNodeId = fromNodeId,
        toNodeId = toNodeId,
        linkQuality = linkQuality,
        lastUpdatedEpochMillis = lastUpdatedEpochMillis
    )

    private fun TopologyEdge.toEntity(): TopologyEdgeEntity = TopologyEdgeEntity(
        fromNodeId = fromNodeId,
        toNodeId = toNodeId,
        linkQuality = linkQuality,
        lastUpdatedEpochMillis = lastUpdatedEpochMillis
    )
}
