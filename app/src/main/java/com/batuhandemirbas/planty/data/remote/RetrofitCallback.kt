package com.batuhandemirbas.planty.data.remote

import android.content.Context
import android.widget.Toast
import com.batuhandemirbas.planty.utils.Util
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


open class RetrofitCallback<T>(context: Context, callback: Callback<T>) : Callback<T> {
    private var mContext: Context = context
    private val mCallback: Callback<T> = callback

    override fun onResponse(call: Call<T>, response: Response<T>) {
        // Do application relavent custom operation like manupulating reponse etc.
        //AlertDialogdismiss(mContext);
        println("-----------BEGIN---------")
        println(" ")
        println("URL      ->" + call.request().url())
        println("METHOD   ->" + call.request().method())
        println("HEADER   ->" + call.request().headers())
        if (call.request().body() != null) {
            println("REQUEST  ->" + bodyToString(call.request().body()))
        } else {
            println("REQUEST  -> null")
        }
        println("RESPONSE ->" + response.body())
        println("Code     ->" + response.code())
        println("Error    ->" + response.errorBody())
        println(" ")
        println("------------END----------")

        if (response.code() == 200) {
            mCallback.onResponse(call, response)
        } else {
            Toast.makeText(
                mContext,
                response.errorBody()
                    .toString() + response.code(), //mContext.getString(R.string.hata_olustu_lutfen_tekrar_deneyiniz) + response.code(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        println("error")
        println(t.message)
        if (!Util().checkInternet(mContext)) {
            Toast.makeText(mContext, t.message, Toast.LENGTH_LONG).show()
            return
        }
        //Alert Here
        mCallback.onFailure(call, t)
    }

    companion object {
        private fun bodyToString(request: RequestBody?): String {
            return try {
                val buffer = Buffer()
                request!!.writeTo(buffer)
                buffer.readUtf8()
            } catch (e: IOException) {
                "did not work"
            }
        }
    }
}
