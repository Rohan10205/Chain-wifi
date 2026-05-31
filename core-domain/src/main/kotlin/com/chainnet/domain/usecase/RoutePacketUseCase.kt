package com.chainnet.domain.usecase

import com.chainnet.domain.repository.RoutingRepository

class RoutePacketUseCase(private val routingRepository: RoutingRepository) {
    suspend fun resolveNextHop(destinationNodeId: String): String? {
        return routingRepository.resolveNextHop(destinationNodeId)
    }
}
