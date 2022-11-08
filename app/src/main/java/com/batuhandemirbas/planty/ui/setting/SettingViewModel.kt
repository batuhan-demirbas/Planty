package com.batuhandemirbas.planty.ui.setting

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SettingUiState(
    val waterLevel: Int? = null
)

class SettingViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

}