package com.chainnet.domain.repository

import com.chainnet.domain.model.Route
import kotlinx.coroutines.flow.Flow

interface RoutingRepository {
    fun observeRoutes(): Flow<List<Route>>
    suspend fun upsertRoute(route: Route)
    suspend fun resolveNextHop(destinationNodeId: String): String?
}
