package com.chainnet.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConfigDataStore(private val dataStore: DataStore<ChainNetConfig>) {
    val config: Flow<ChainNetConfig> = dataStore.data

    suspend fun updateNodeId(nodeId: String) {
        dataStore.updateData { it.toBuilder().setNodeId(nodeId).build() }
    }

    suspend fun updatePreferredRole(role: String) {
        dataStore.updateData { it.toBuilder().setPreferredRole(role).build() }
    }

    suspend fun updateLastKnownNetwork(ssid: String, passphrase: String) {
        dataStore.updateData {
            it.toBuilder().setLastKnownSsid(ssid).setLastKnownPassphrase(passphrase).build()
        }
    }

    fun observeOverlayEnabled(): Flow<Boolean> = config.map { it.enableOverlay }

    companion object {
        fun create(context: Context): ConfigDataStore {
            val store = DataStoreFactory.create(
                serializer = ChainNetConfigSerializer,
                produceFile = { context.dataStoreFile("chainnet_config.pb") }
            )
            return ConfigDataStore(store)
        }
    }
}
