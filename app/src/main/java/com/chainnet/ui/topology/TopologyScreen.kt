package com.chainnet.ui.topology

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.chainnet.ui.components.TopologyCanvas

@Composable
fun TopologyScreen(padding: PaddingValues, viewModel: TopologyViewModel = hiltViewModel()) {
    val topology by viewModel.topology.collectAsState()
    TopologyCanvas(
        graph = topology,
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    )
}
