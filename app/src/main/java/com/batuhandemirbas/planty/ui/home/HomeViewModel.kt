package com.batuhandemirbas.planty.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.batuhandemirbas.planty.data.model.Plant
import com.batuhandemirbas.planty.data.remote.RetrofitCallback
import com.batuhandemirbas.planty.data.remote.RetrofitClient
import com.batuhandemirbas.planty.data.remote.RetrofitService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

data class HomeUiState(
    val userPlant: Plant? = null,
    val plantyData: JsonObject? = null,
    val isMotorOn: JsonObject? = null

)

class HomeViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val db = Firebase.firestore

    fun getPlantData() {
        val plantRef = db.collection("plants").document("bonsai")

        plantRef.addSnapshotListener { documentSnapshot, _ ->
            val plant = documentSnapshot?.toObject<Plant>()

            _uiState.update { currentState ->

                currentState.copy(userPlant = plant)

            }

        }

    }

    fun getPlantyLastData(context: Context) {

        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                val call = RetrofitClient.getApiClient(context).create(RetrofitService::class.java)
                    .getPlantyLastData()

                call.enqueue(RetrofitCallback(context, object : Callback<JsonObject> {
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        if (response.code() == 200) {

                            _uiState.update { currentState ->

                                currentState.copy(plantyData = response.body())

                            }
                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Toast.makeText(context, t.message + "homeview error", Toast.LENGTH_SHORT).show()
                    }

                }))
            }
        }


        timer.schedule(task, 0, 5000)

    }

}