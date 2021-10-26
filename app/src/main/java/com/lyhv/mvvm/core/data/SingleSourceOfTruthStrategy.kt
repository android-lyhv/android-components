package com.lyhv.mvvm.core.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

fun <T> getFlow(
    networkCall: suspend () -> Result<T>
): Flow<Result<T>> = flow {
    emit(Result.Loading)
    val responseStatus = networkCall.invoke()
    emit(responseStatus)
}.flowOn(Dispatchers.IO)

/**
 * Returns a [Flow] whose values are cached in local storage
 */
fun <T, A> resultFlow(
    databaseQuery: () -> Flow<T>,
    networkCall: suspend () -> Result<A>,
    saveCallResult: suspend (A) -> Unit,
    emitLoading: Boolean = true
): Flow<Result<T>> = flow {
    if (emitLoading) emit(Result.Loading)
    val source = databaseQuery.invoke().map { Result.Success(it) }

    val responseStatus = networkCall.invoke()
    if (responseStatus is Result.Success) {
        saveCallResult(responseStatus.data!!)
        emitAll(source)
    } else if (responseStatus is Result.Failure) {
        emit(
            Result.Failure(
                message = responseStatus.message,
                status = responseStatus.status,
                errors = responseStatus.errors
            )
        )
    }
}.flowOn(Dispatchers.IO)

/**
 * Returns a [Flow] whose values are cached in memory
 */
fun <A> resultFlowNoCache(
    networkCall: suspend () -> Result<A>,
    emitLoading: Boolean = true
): Flow<Result<A>> = flow<Result<A>> {
    if (emitLoading) emit(Result.Loading)
    val responseStatus = networkCall.invoke()
    if (responseStatus is Result.Success) {
        emit(Result.Success(responseStatus.data!!))
    } else if (responseStatus is Result.Failure) {
        emit(
            Result.Failure(
                message = responseStatus.message,
                status = responseStatus.status,
                errors = responseStatus.errors
            )
        )
    }
}.flowOn(Dispatchers.IO)

fun <T> getFirstResultFailure(vararg results: Result<*>): Result.Failure<T> {
    val firstResultFailure = results.firstOrNull { it is Result.Failure } as Result.Failure
    return Result.Failure<T>(
        message = firstResultFailure.message,
        status = firstResultFailure.status,
        errors = firstResultFailure.errors
    )
}