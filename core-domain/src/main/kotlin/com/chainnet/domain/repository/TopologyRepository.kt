package com.chainnet.domain.repository

import com.chainnet.domain.model.TopologyGraph
import kotlinx.coroutines.flow.Flow

interface TopologyRepository {
    fun observeTopology(): Flow<TopologyGraph>
    suspend fun updateTopology(graph: TopologyGraph)
}
