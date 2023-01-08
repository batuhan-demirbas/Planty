package com.batuhandemirbas.planty

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.batuhandemirbas.planty.data.remote.RetrofitCallback
import com.batuhandemirbas.planty.data.remote.RetrofitClient
import com.batuhandemirbas.planty.data.remote.RetrofitService
import com.google.gson.JsonPrimitive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class MainUiState(
    val isMotorOn: JsonPrimitive? = null

)

class MainViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()


    fun runMotor(context: Context) {

        val call =
            RetrofitClient.getApiClient(context).create(RetrofitService::class.java)
                .postMotor()

        call.enqueue(RetrofitCallback(context, object : Callback<JsonPrimitive> {
            override fun onResponse(
                call: Call<JsonPrimitive>,
                response: Response<JsonPrimitive>
            ) {
                if (response.code() == 200) {

                    _uiState.update { currentState ->

                        currentState.copy(isMotorOn = response.body())

                    }
                }
            }

            override fun onFailure(call: Call<JsonPrimitive>, t: Throwable) {
                Toast.makeText(context, t.message + "Main", Toast.LENGTH_SHORT).show()
            }

        }))

    }

}