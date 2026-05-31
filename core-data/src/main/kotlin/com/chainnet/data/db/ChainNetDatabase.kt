package com.chainnet.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chainnet.data.db.dao.PeerDao
import com.chainnet.data.db.dao.RouteDao
import com.chainnet.data.db.dao.TopologyEdgeDao
import com.chainnet.data.db.entity.PeerEntity
import com.chainnet.data.db.entity.RouteEntity
import com.chainnet.data.db.entity.TopologyEdgeEntity

@Database(
    entities = [PeerEntity::class, TopologyEdgeEntity::class, RouteEntity::class],
    version = 1,
    exportSchema = true
)
abstract class ChainNetDatabase : RoomDatabase() {
    abstract fun peerDao(): PeerDao
    abstract fun topologyEdgeDao(): TopologyEdgeDao
    abstract fun routeDao(): RouteDao
}
