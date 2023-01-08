package com.batuhandemirbas.planty.ui.settings

import android.view.View
import androidx.lifecycle.ViewModel
import com.batuhandemirbas.planty.data.model.Plant
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingUiState(
    var userPlant: Plant? = null

)

class SettingsViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    private val db = Firebase.firestore

    fun getUserPlantsData() {
        val userPlantsRef = db.collection("plants").document("bonsai")

        userPlantsRef.addSnapshotListener { documentSnapshot, _ ->
            val userPlant = documentSnapshot?.toObject<Plant>()

            _uiState.update { currentState ->

                currentState.copy(userPlant = userPlant)

            }

        }

    }

    fun updateUserPlantsData(view: View, name: String, type: String) {
        val userPlantsRef = db.collection("plants").document("bonsai")

        val changeList = mapOf(
            "name" to name,
            "type" to type
        )

        userPlantsRef.update(changeList)
            .addOnSuccessListener {

                Snackbar.make(view, "Bilgiler değiştirildi", Snackbar.LENGTH_SHORT).show()

            }
            .addOnFailureListener {

                Snackbar.make(view, "Bilgiler değiştirilemedi", Snackbar.LENGTH_SHORT).show()

            }

    }


}