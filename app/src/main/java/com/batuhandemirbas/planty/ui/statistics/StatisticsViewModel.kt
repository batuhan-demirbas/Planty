package com.batuhandemirbas.planty.ui.statistics

import android.content.Context
import androidx.lifecycle.ViewModel
import com.batuhandemirbas.planty.data.model.Feeds
import com.batuhandemirbas.planty.data.remote.RetrofitCallback
import com.batuhandemirbas.planty.data.remote.RetrofitClient
import com.batuhandemirbas.planty.data.remote.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class StatisticsUiState(
    val plantyData: Feeds? = null

)

class StatisticsViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    fun getWeeklyAverageData(context: Context, start: String, end: String) {

        val call = RetrofitClient.getApiClient(context).create(RetrofitService::class.java)
            .getWeeklyAverageData(start, end)

        call.enqueue(RetrofitCallback(context, object : Callback<Feeds> {
            override fun onResponse(
                call: Call<Feeds>,
                response: Response<Feeds>
            ) {
                if (response.code() == 200) {

                    _uiState.update { currentState ->

                        currentState.copy(plantyData = response.body())

                    }
                }
            }

            override fun onFailure(call: Call<Feeds>, t: Throwable) {
                println(t.message)
            }

        }))

    }

}