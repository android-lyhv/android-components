package com.lyho.androidcore.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lyho.androidcore.model.entities.Result
import com.lyho.androidcore.model.entities.User
import com.lyho.androidcore.model.network.ApiError
import com.lyho.androidcore.model.network.ResultCallBack
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
class UserRepositoryTest(application: Application) : BaseRepository(application), IUserRepository {
    override suspend fun getUser(userId: Int): Result<User> {
        return suspendCancellableCoroutine {
            val call = apiService.getUser(userId)
            it.invokeOnCancellation { thrower ->
                it.resume(Result.Error(Exception("aaa $userId")))
                call.cancel()
            }
            call.enqueue(object : ResultCallBack<User>() {
                override fun success(t: User?) {
                    it.resume(Result.Success(User()))
                }

                override fun failure(apiError: ApiError?) {
                    it.resume(Result.Error(Exception(apiError?.message)))
                }
            })
        }
    }

    override fun getUserLiveData(userId: Int): LiveData<User> {
        val liveData = MutableLiveData<User>()
        apiService.getUser(userId).enqueue(object : ResultCallBack<User>() {
            override fun success(t: User?) {
                liveData.postValue(t)
            }

            override fun failure(apiError: ApiError?) {
                liveData.postValue(null)
            }
        })
        return liveData
    }

    override fun getUserAsync(userId: Int, apiCallBack: ResultCallBack<User>) {
        mAppExecutors.diskIO.execute {
            try {
                val user = mDataDao?.getUsers(userId)
                apiCallBack.success(user)
            } catch (e: Exception) {
                apiCallBack.failure(ApiError().apply {
                    message = e.message ?: ""
                })
            }
        }
    }
}
