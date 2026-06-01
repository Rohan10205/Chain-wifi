package com.chainnet.network.routing

import com.chainnet.domain.model.Route
import com.chainnet.domain.repository.RoutingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AodvRoutingEngine(private val routingRepository: RoutingRepository) {
    suspend fun handleRouteRequest(originNodeId: String, destinationNodeId: String, hopCount: Int) {
        val route = Route(
            destinationNodeId = destinationNodeId,
            nextHopNodeId = originNodeId,
            hopCount = hopCount,
            expiresAtEpochMillis = System.currentTimeMillis() + 60_000
        )
        withContext(Dispatchers.IO) {
            routingRepository.upsertRoute(route)
        }
    }

    suspend fun handleRouteReply(destinationNodeId: String, nextHopNodeId: String, hopCount: Int) {
        val route = Route(
            destinationNodeId = destinationNodeId,
            nextHopNodeId = nextHopNodeId,
            hopCount = hopCount,
            expiresAtEpochMillis = System.currentTimeMillis() + 120_000
        )
        withContext(Dispatchers.IO) {
            routingRepository.upsertRoute(route)
        }
    }
}
