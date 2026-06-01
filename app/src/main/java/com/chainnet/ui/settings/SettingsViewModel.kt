package com.chainnet.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chainnet.data.datastore.ConfigDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val configDataStore: ConfigDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state

    init {
        viewModelScope.launch {
            configDataStore.config.collectLatest { config ->
                _state.value = SettingsState(
                    nodeId = config.nodeId,
                    preferredRole = config.preferredRole,
                    overlayEnabled = config.enableOverlay
                )
            }
        }
    }
}

data class SettingsState(
    val nodeId: String = "",
    val preferredRole: String = "",
    val overlayEnabled: Boolean = false
)
