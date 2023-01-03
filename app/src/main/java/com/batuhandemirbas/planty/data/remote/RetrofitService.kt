package com.batuhandemirbas.planty.data.remote

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("/channels/1998702/feeds.json?api_key=H1CUY7ARLIHYX6NK&results=1")
    fun getPlantyLastData(): Call<JsonObject>

    @GET("/channels/1998702/feeds.json?api_key=H1CUY7ARLIHYX6NK")
    fun getPlantyData(): Call<JsonObject>

}
