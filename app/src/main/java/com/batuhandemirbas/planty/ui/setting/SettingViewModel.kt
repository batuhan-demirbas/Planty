package com.batuhandemirbas.planty.ui.setting

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.batuhandemirbas.planty.R
import com.batuhandemirbas.planty.domain.model.UserPlant
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingUiState(
    var userPlant: UserPlant? = null

)

class SettingViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    private val db = Firebase.firestore

    fun getUserPlantsData() {
        val userPlantsRef = db.collection("myPlants").document("yUbAs0EsHfRu0ZJTRvaI")

        userPlantsRef.addSnapshotListener { documentSnapshot, error ->
            val userPlant = documentSnapshot?.toObject<UserPlant>()

            _uiState.update { currentState ->

                currentState.copy(userPlant = userPlant)

            }

        }

    }

    fun updateUserPlantsData(view: View, name: String, type: String) {
        val userPlantsRef = db.collection("myPlants").document("yUbAs0EsHfRu0ZJTRvaI")

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