package com.lyhv.component.model.network

import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

/**
 * @author Ly Ho V.
 */
open class ResultCallBack<T> : Callback<T> {
    companion object {
        private const val SERVER_ERROR = "Your internet connection appears to be offline"
    }

    open fun success(t: T?) {}

    open fun failure(apiError: ApiError?) {}

    final override fun onResponse(call: Call<T>, response: Response<T>) {
        Log.d("aaa", "Ok")
        when {
            response.isSuccessful -> success(response.body())
            response.errorBody() != null -> try {
                val apiError =
                    Gson().fromJson(response.errorBody().toString(), ApiError::class.java)
                if (apiError.message.isBlank()) {
                    apiError.message = SERVER_ERROR
                }
                failure(apiError)
            } catch (e: Exception) {
                failure(ApiError(response.code(), SERVER_ERROR))
            }
            else -> failure(ApiError(response.code(), SERVER_ERROR))
        }
    }

    final override fun onFailure(call: Call<T>, t: Throwable) {
        Log.d("aaa", t.message)
        failure(ApiError(HttpURLConnection.HTTP_CLIENT_TIMEOUT, SERVER_ERROR))
    }
}
