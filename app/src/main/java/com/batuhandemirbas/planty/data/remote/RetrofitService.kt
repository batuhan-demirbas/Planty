package com.batuhandemirbas.planty.data.remote

import com.batuhandemirbas.planty.data.model.Feeds
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("/channels/1998702/feeds.json?api_key=H1CUY7ARLIHYX6NK&results=1")
    fun getPlantyLastData(): Call<JsonObject>

    @GET("/channels/1998702/feeds.json?api_key=H1CUY7ARLIHYX6NK&average=daily")
    fun getWeeklyAverageData(@Query("start") start: String, @Query("end") end: String): Call<Feeds>

    @GET("update?api_key=9ASGD1N3CN30CXB3&field5=1")
    fun postMotor(): Call<JsonPrimitive>

}
