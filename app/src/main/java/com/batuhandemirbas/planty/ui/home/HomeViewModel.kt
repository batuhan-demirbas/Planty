package com.batuhandemirbas.planty.ui.home

import androidx.lifecycle.ViewModel
import com.batuhandemirbas.planty.domain.model.UserPlant
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeUiState(
    var userPlant: UserPlant? = null

)

class HomeViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val db = Firebase.firestore

    fun getUserPlantsData() {
        val userPlantsRef = db.collection("myPlants").document("yUbAs0EsHfRu0ZJTRvaI")

        userPlantsRef.addSnapshotListener {  documentSnapshot, error ->
            val userPlant = documentSnapshot?.toObject<UserPlant>()

            _uiState.update { currentState ->

                currentState.copy(userPlant = userPlant)

            }

        }

    }

}