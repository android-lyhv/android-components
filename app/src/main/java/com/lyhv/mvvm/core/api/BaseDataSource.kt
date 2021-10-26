package com.lyhv.mvvm.core.api

import com.lyhv.mvvm.core.data.Result
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

abstract class BaseDataSource {
    companion object {
        const val INVALID_STATUS = -1
    }

    suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.Success(body)
                if (response.code() == 204) return Result.SuccessEmptyBody
            }
            getResultFailure(response)
        } catch (e: Exception) {
            e.printStackTrace()
            when (e) {
                is IOException -> // mean something wrong with network connection
                    Result.Failure(
                        message = "",
                        status = INVALID_STATUS
                    )
                else -> getResultFailure()
            }
        }
    }

    private fun <T> getResultFailure(response: Response<T>): Result<T> {
        val errorString = response.errorBody()?.string() ?: "{}"
        return if (JSONObject(errorString).optString("meta").isNotBlank()) {
            val errorJson = JSONObject(errorString).getJSONObject("meta")
            val status = errorJson.optInt("status")
            val message = errorJson.optString("message")
            val errors = errorJson.optJSONObject("errors")
            Result.Failure(
                status = status,
                message = message,
                errors = errors
            )
        } else {
            getResultFailure()
        }
    }

    private fun <T> getResultFailure(): Result<T> {
        return Result.Failure(
            status = INVALID_STATUS,
            message = ""
        )
    }
}