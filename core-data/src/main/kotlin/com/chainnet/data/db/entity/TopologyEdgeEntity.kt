package com.chainnet.data.db.entity

import androidx.room.Entity

@Entity(
    tableName = "topology_edges",
    primaryKeys = ["fromNodeId", "toNodeId"]
)
data class TopologyEdgeEntity(
    val fromNodeId: String,
    val toNodeId: String,
    val linkQuality: Int,
    val lastUpdatedEpochMillis: Long
)
