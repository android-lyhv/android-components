package com.lyho.androidbase.model.network
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

/**
 * @author Ly Ho V.
 */
open class ApiCallback<T> : Callback<T> {
    companion object {
        private const val SERVER_ERROR = "Your internet connection appears to be offline"
    }

    open fun success(t: T?) {}

    open fun failure(apiError: ApiError?) {}

    override fun onResponse(call: Call<T>, response: Response<T>) {
        when {
            response.isSuccessful -> success(response.body())
            response.errorBody() != null -> try {
                val apiError = Gson().fromJson(response.errorBody().string(), ApiError::class.java)
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

    override fun onFailure(call: Call<T>, t: Throwable) {
        failure(ApiError(HttpURLConnection.HTTP_CLIENT_TIMEOUT, SERVER_ERROR))
    }
}
