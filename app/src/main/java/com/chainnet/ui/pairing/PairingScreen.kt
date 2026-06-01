package com.chainnet.ui.pairing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PairingScreen(padding: PaddingValues, viewModel: PairingViewModel = hiltViewModel()) {
    val qrState by viewModel.qrState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { viewModel.generateQr() }) {
            Text("Generate QR")
        }
        qrState.bitmap?.let { bitmap ->
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = "QR Code")
        }
        Text(text = qrState.payload)
    }
}
