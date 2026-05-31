package com.chainnet.domain.usecase

import com.chainnet.domain.repository.PeerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiscoverPeersUseCase(private val peerRepository: PeerRepository) {
    operator fun invoke(): Flow<Int> {
        return peerRepository.observePeers().map { it.size }
    }
}
