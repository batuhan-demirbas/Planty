package com.batuhandemirbas.planty.ui.statistics

import android.content.Context
import androidx.lifecycle.ViewModel
import com.batuhandemirbas.planty.data.model.Feeds
import com.batuhandemirbas.planty.data.model.UserPlant
import com.batuhandemirbas.planty.data.remote.RetrofitCallback
import com.batuhandemirbas.planty.data.remote.RetrofitClient
import com.batuhandemirbas.planty.data.remote.RetrofitService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

data class StatisticsUiState(
    val plantyData: Feeds? = null

)

class StatisticsViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    fun getPlantyData(context: Context) {

        val call = RetrofitClient.getApiClient(context).create(RetrofitService::class.java)
            .getPlantyData()

        call.enqueue(RetrofitCallback(context, object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (response.code() == 200) {

                    _uiState.update { currentState ->

                        currentState.copy(plantyData = Gson().fromJson(response.body(), Feeds::class.java))

                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

        }))

    }

}