package com.lyhv.mvvm.core.data

import org.json.JSONObject

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    object SuccessEmptyBody : Result<Nothing>()
    data class Failure<out T>(
        val message: String,
        val status: Int,
        val errors: JSONObject? = null
    ) : Result<T>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is SuccessEmptyBody -> "SuccessEmptyBody"
            is Failure -> "Error[exception=$message]"
            is Loading -> "Loading"
        }
    }
}