package com.lyho.androidcore.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lyho.androidcore.model.entities.User
import com.lyho.androidcore.model.network.Result
import com.lyho.androidcore.model.network.ApiError

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
class UserRepository(application: Application) : BaseRepository(application), IUserRepository {
    override fun getUserLiveData(userId: Int): LiveData<User> {
        val liveData = MutableLiveData<User>()
        apiService.getUser(userId).enqueue(object : Result<User>() {
            override fun success(t: User?) {
                liveData.postValue(t)
            }

            override fun failure(apiError: ApiError?) {
                liveData.postValue(null)
            }
        })
        return liveData
    }

    override fun getUserAsync(userId: Int, apiCallBack: Result<User>) {
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
