package com.lyho.androidbase.model.repository

import android.app.Application
import com.lyho.androidbase.model.entities.User
import com.lyho.androidbase.model.network.ApiCallback
import com.lyho.androidbase.model.network.ApiError

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
class UserRepository(application: Application) : BaseRepository(application), IUserRepository {
    override fun getUserAsync(userId: Int, apiCallBack: ApiCallback<User>) {
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
