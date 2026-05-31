package com.chainnet.domain.repository

import com.chainnet.domain.model.ChainState
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    fun observeChainState(): Flow<ChainState>
    suspend fun startChain()
    suspend fun stopChain()
    suspend fun connectUpstream(ssid: String, passphrase: String)
}
