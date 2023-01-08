package com.batuhandemirbas.planty.ui.info

import androidx.lifecycle.ViewModel
import com.batuhandemirbas.planty.data.model.Plant
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class InfoBottomSheetUiState(
    val plant: Plant? = null
)

class InfoBottomSheetViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(InfoBottomSheetUiState())
    val uiState: StateFlow<InfoBottomSheetUiState> = _uiState.asStateFlow()

    private val db = Firebase.firestore

    fun getPlantData() {
        val plantRef = db.collection("plants").document("bonsai")

        plantRef.addSnapshotListener { documentSnapshot, _ ->
            val plant = documentSnapshot?.toObject<Plant>()

            _uiState.update { currentState ->

                currentState.copy(plant = plant)

            }

        }

    }

    // how many days has it been since the plant was planted
    fun getPlantAge(platingDate: String): String {
        val now = LocalDate.now()

        // number of days between 2 current dates
        val days =
            now.toEpochDay() - LocalDate.parse(platingDate, DateTimeFormatter.ISO_DATE).toEpochDay()

        return "$days days"
    }

}