package com.chainnet.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chainnet.data.db.entity.TopologyEdgeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopologyEdgeDao {
    @Query("SELECT * FROM topology_edges")
    fun observeEdges(): Flow<List<TopologyEdgeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(edge: TopologyEdgeEntity)

    @Query("DELETE FROM topology_edges")
    suspend fun clearAll()
}
