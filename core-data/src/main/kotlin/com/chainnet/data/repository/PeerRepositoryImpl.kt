package com.chainnet.data.repository

import com.chainnet.data.db.dao.PeerDao
import com.chainnet.data.db.entity.PeerEntity
import com.chainnet.domain.model.NodeCapabilities
import com.chainnet.domain.model.NodeIdentity
import com.chainnet.domain.model.NetworkRole
import com.chainnet.domain.model.PeerNode
import com.chainnet.domain.repository.PeerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PeerRepositoryImpl(private val peerDao: PeerDao) : PeerRepository {
    override fun observePeers(): Flow<List<PeerNode>> {
        return peerDao.observePeers().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun upsertPeer(peer: PeerNode) {
        peerDao.upsert(peer.toEntity())
    }

    override suspend fun markPeerDisconnected(nodeId: String) {
        peerDao.deleteById(nodeId)
    }

    private fun PeerEntity.toDomain(): PeerNode = PeerNode(
        identity = NodeIdentity(nodeId, publicKey, displayName),
        ipAddress = ipAddress,
        hopCount = hopCount,
        role = NetworkRole.valueOf(role),
        lastSeenEpochMillis = lastSeenEpochMillis,
        capabilities = NodeCapabilities(
            supportsApSta = supportsApSta,
            supportsWpa3 = supportsWpa3,
            batteryPercent = batteryPercent,
            signalLevel = signalLevel
        )
    )

    private fun PeerNode.toEntity(): PeerEntity = PeerEntity(
        nodeId = identity.id,
        ipAddress = ipAddress,
        hopCount = hopCount,
        role = role.name,
        lastSeenEpochMillis = lastSeenEpochMillis,
        supportsApSta = capabilities.supportsApSta,
        supportsWpa3 = capabilities.supportsWpa3,
        batteryPercent = capabilities.batteryPercent,
        signalLevel = capabilities.signalLevel,
        publicKey = identity.publicKey,
        displayName = identity.displayName
    )
}
