package com.chainnet.ui.topology

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chainnet.domain.model.TopologyGraph
import com.chainnet.domain.usecase.ObserveTopologyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TopologyViewModel @Inject constructor(
    observeTopologyUseCase: ObserveTopologyUseCase
) : ViewModel() {
    private val _topology = MutableStateFlow(TopologyGraph(emptyList(), emptyList()))
    val topology: StateFlow<TopologyGraph> = _topology

    init {
        viewModelScope.launch {
            observeTopologyUseCase().collect { graph ->
                _topology.value = graph
            }
        }
    }
}
