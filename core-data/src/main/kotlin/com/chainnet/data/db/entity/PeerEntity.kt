package com.chainnet.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "peers")
data class PeerEntity(
    @PrimaryKey val nodeId: String,
    val ipAddress: String,
    val hopCount: Int,
    val role: String,
    val lastSeenEpochMillis: Long,
    val supportsApSta: Boolean,
    val supportsWpa3: Boolean,
    val batteryPercent: Int,
    val signalLevel: Int,
    val publicKey: ByteArray,
    val displayName: String?
)
