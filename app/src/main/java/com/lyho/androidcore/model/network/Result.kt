package com.lyho.androidcore.model.network

/**
 * Created by lyhv on September 10, 2019
 * Copyright @ est-rouge. All rights reserved
 */

sealed class Result<out R> {
    data class Data<out T>(val data: T) : Result<T>()
    data class Error(var code: Int = 0, var message: String = "", var description: String = "") :
        Result<Nothing>()
}
