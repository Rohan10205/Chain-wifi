package com.chainnet.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chainnet.domain.model.ChainState
import com.chainnet.domain.model.NetworkRole
import com.chainnet.domain.repository.NetworkRepository
import com.chainnet.domain.usecase.DiscoverPeersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class ChainStatusViewModel @Inject constructor(
    networkRepository: NetworkRepository,
    discoverPeersUseCase: DiscoverPeersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(
        ChainStatusState(
            role = NetworkRole.EDGE,
            hopCount = 0,
            peerCount = 0,
            isUpstreamConnected = false,
            isHotspotActive = false
        )
    )
    val state: StateFlow<ChainStatusState> = _state

    init {
        viewModelScope.launch {
            combine(networkRepository.observeChainState(), discoverPeersUseCase()) { chainState, peerCount ->
                chainState to peerCount
            }.collect { (chainState, peerCount) ->
                _state.value = ChainStatusState.from(chainState, peerCount)
            }
        }
    }
}

data class ChainStatusState(
    val role: NetworkRole,
    val hopCount: Int,
    val peerCount: Int,
    val isUpstreamConnected: Boolean,
    val isHotspotActive: Boolean
) {
    companion object {
        fun from(chainState: ChainState, peerCount: Int): ChainStatusState {
            return ChainStatusState(
                role = chainState.role,
                hopCount = chainState.hopCount,
                peerCount = peerCount,
                isUpstreamConnected = chainState.isUpstreamConnected,
                isHotspotActive = chainState.isHotspotActive
            )
        }
    }
}
