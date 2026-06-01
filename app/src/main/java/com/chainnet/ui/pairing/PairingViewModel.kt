package com.chainnet.ui.pairing

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.chainnet.data.datastore.ConfigDataStore
import com.chainnet.network.discovery.QrCodeManager
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class PairingViewModel @Inject constructor(
    private val configDataStore: ConfigDataStore,
    private val qrCodeManager: QrCodeManager
) : ViewModel() {
    private val _qrState = MutableStateFlow(QrState())
    val qrState: StateFlow<QrState> = _qrState

    fun generateQr() {
        val payload = qrCodeManager.encodeCredentials("ChainNet", "change-me")
        val encoder = BarcodeEncoder()
        val bitmap: Bitmap = encoder.encodeBitmap(payload, BarcodeFormat.QR_CODE, 600, 600)
        _qrState.value = QrState(payload = payload, bitmap = bitmap)
    }
}

data class QrState(
    val payload: String = "",
    val bitmap: Bitmap? = null
)
