package com.chainnet.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(padding: PaddingValues, viewModel: ChainStatusViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Role: ${'$'}{state.role}", style = MaterialTheme.typography.titleLarge)
        Text(text = "Hop count: ${'$'}{state.hopCount}")
        Text(text = "Peers: ${'$'}{state.peerCount}")
        Text(text = "Upstream connected: ${'$'}{state.isUpstreamConnected}")
        Text(text = "Hotspot active: ${'$'}{state.isHotspotActive}")
    }
}
