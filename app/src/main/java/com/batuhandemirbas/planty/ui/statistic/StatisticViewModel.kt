package com.batuhandemirbas.planty.ui.statistic

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class StatisticUiState(
    val waterLevel: Int? = null
)

class StatisticViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(StatisticUiState())
    val uiState: StateFlow<StatisticUiState> = _uiState.asStateFlow()

}