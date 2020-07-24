package com.lyhv.component.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
fun <T> getFlow(
    networkCall: suspend () -> Result<T>
): Flow<Result<T>> = flow {
    emit(Result.Loading())
    val responseStatus = networkCall.invoke()
    emit(responseStatus)
}.flowOn(Dispatchers.IO)

fun <T, A> resultLiveData(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Result<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Result<T>> =
    liveData(Dispatchers.IO) {
        emit(Result.Loading())
        val source = databaseQuery.invoke().map { Result.Success(it) as Result<T> }

        val responseStatus = networkCall.invoke()
        if (responseStatus is Result.Success) {
            saveCallResult(responseStatus.data!!)
        }
        emitSource(source)
    }

fun <T> resultLiveDataNoCache(
    networkCall: suspend () -> Result<T>
): LiveData<Result<T>> = liveData(Dispatchers.IO) {
    emit(Result.Loading())
    val responseStatus = networkCall.invoke()
    if (responseStatus is Result.Success) {
        emit(responseStatus)
    } else if (responseStatus is Result.Failure) {
        emit(responseStatus)
    }
}

fun <T> resultLiveEvent(
    networkCall: suspend () -> Result<T>
): LiveData<Result<T>> =
    liveData(Dispatchers.IO) {
        emit(Result.Loading())
        val responseStatus = networkCall.invoke()
        emit(responseStatus)
    }

suspend fun <T> resultCachingData(
    networkCall: suspend () -> Result<T>,
    saveCallResult: suspend (T) -> Unit,
    onFailed: (Boolean) -> Unit = {}
) = withContext(Dispatchers.IO) {
    val responseStatus = networkCall.invoke()
    if (responseStatus is Result.Success) {
        saveCallResult(responseStatus.data!!)
        onFailed.invoke(false)
    } else if (responseStatus is Result.Failure) {
        onFailed.invoke(true)
    }
}