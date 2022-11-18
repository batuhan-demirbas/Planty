package com.batuhandemirbas.planty.ui.notification

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class NotificationUiState(
    val waterLevel: Int? = null
)

class NotificationViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

}