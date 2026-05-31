package com.chainnet.data.repository

import com.chainnet.data.db.dao.RouteDao
import com.chainnet.data.db.entity.RouteEntity
import com.chainnet.domain.model.Route
import com.chainnet.domain.repository.RoutingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoutingRepositoryImpl(private val routeDao: RouteDao) : RoutingRepository {
    override fun observeRoutes(): Flow<List<Route>> {
        return routeDao.observeRoutes().map { routes -> routes.map { it.toDomain() } }
    }

    override suspend fun upsertRoute(route: Route) {
        routeDao.upsert(route.toEntity())
    }

    override suspend fun resolveNextHop(destinationNodeId: String): String? {
        return routeDao.getNextHop(destinationNodeId)
    }

    private fun RouteEntity.toDomain(): Route = Route(
        destinationNodeId = destinationNodeId,
        nextHopNodeId = nextHopNodeId,
        hopCount = hopCount,
        expiresAtEpochMillis = expiresAtEpochMillis
    )

    private fun Route.toEntity(): RouteEntity = RouteEntity(
        destinationNodeId = destinationNodeId,
        nextHopNodeId = nextHopNodeId,
        hopCount = hopCount,
        expiresAtEpochMillis = expiresAtEpochMillis
    )
}
