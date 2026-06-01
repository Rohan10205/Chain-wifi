package com.chainnet.ui.home

import com.chainnet.domain.model.ChainState
import com.chainnet.domain.model.NetworkRole
import com.chainnet.domain.model.NodeCapabilities
import com.chainnet.domain.model.NodeIdentity
import com.chainnet.domain.model.PeerNode
import com.chainnet.domain.repository.NetworkRepository
import com.chainnet.domain.repository.PeerRepository
import com.chainnet.domain.usecase.DiscoverPeersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChainStatusViewModelTest {
    @Test
    fun `state reflects repository updates`() = runTest(UnconfinedTestDispatcher()) {
        val networkRepository = FakeNetworkRepository()
        val peerRepository = FakePeerRepository()
        val discoverPeersUseCase = DiscoverPeersUseCase(peerRepository)

        val viewModel = ChainStatusViewModel(networkRepository, discoverPeersUseCase)

        networkRepository.updateState(
            ChainState(
                role = NetworkRole.RELAY,
                hopCount = 2,
                isUpstreamConnected = true,
                isHotspotActive = true
            )
        )
        peerRepository.emitPeers(3)

        val state = viewModel.state.value
        assertEquals(NetworkRole.RELAY, state.role)
        assertEquals(2, state.hopCount)
        assertEquals(3, state.peerCount)
    }
}

private class FakeNetworkRepository : NetworkRepository {
    private val stateFlow = MutableStateFlow(
        ChainState(
            role = NetworkRole.EDGE,
            hopCount = 0,
            isUpstreamConnected = false,
            isHotspotActive = false
        )
    )

    override fun observeChainState(): Flow<ChainState> = stateFlow

    override suspend fun startChain() = Unit

    override suspend fun stopChain() = Unit

    override suspend fun connectUpstream(ssid: String, passphrase: String) = Unit

    fun updateState(state: ChainState) {
        stateFlow.value = state
    }
}

private class FakePeerRepository : PeerRepository {
    private val peersFlow = MutableStateFlow(emptyList<PeerNode>())

    override fun observePeers(): Flow<List<PeerNode>> = peersFlow

    override suspend fun upsertPeer(peer: PeerNode) = Unit

    override suspend fun markPeerDisconnected(nodeId: String) = Unit

    fun emitPeers(count: Int) {
        val peers = (0 until count).map { index ->
            PeerNode(
                identity = NodeIdentity("node-${'$'}index", byteArrayOf(), null),
                ipAddress = "192.168.0.${'$'}index",
                hopCount = index,
                role = NetworkRole.EDGE,
                lastSeenEpochMillis = System.currentTimeMillis(),
                capabilities = NodeCapabilities(true, true, 100, -40)
            )
        }
        peersFlow.value = peers
    }
}
