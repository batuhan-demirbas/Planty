package com.batuhandemirbas.planty.data.remote

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private var CONNECTION_TIMEOUT = 60

    //public static final String BASE_URL = Constants.BaseUrl;
    private val baseUrl: String
        get() {
            return "https://api.thingspeak.com"
        }

    fun getApiClient(context: Context?): Retrofit {
        context?.let {

            val httpClient = OkHttpClient.Builder()
                .readTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .addNetworkInterceptor { chain: Interceptor.Chain ->

                    val request = chain.request().newBuilder()
                        .header("Accept-Language", "tr-TR")

                    chain.proceed(request.build())
                }.build()
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit!!

        } ?: run {
            retrofit?.let {
                return it
            } ?: run {
                val httpClient = OkHttpClient.Builder()
                    .readTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .addNetworkInterceptor { chain: Interceptor.Chain ->
                        val request = chain.request().newBuilder()
                            .header("Accept-Language", "tr-TR").build()
                        chain.proceed(request)
                    }.build()
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                return retrofit!!
            }
        }

    }
}