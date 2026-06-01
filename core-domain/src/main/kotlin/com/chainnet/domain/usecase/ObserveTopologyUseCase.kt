package com.chainnet.domain.usecase

import com.chainnet.domain.model.TopologyGraph
import com.chainnet.domain.repository.TopologyRepository
import kotlinx.coroutines.flow.Flow

class ObserveTopologyUseCase(private val topologyRepository: TopologyRepository) {
    operator fun invoke(): Flow<TopologyGraph> = topologyRepository.observeTopology()
}
