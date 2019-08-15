package com.lyho.androidbase.model.repository

import android.app.Application
import com.lyho.androidbase.model.entities.User
import com.lyho.androidbase.model.remote.ApiCallback

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
class UserRepository(application: Application) : BaseRepository(application), IUserRepository {
    override fun getUser(userId: Int, apiCallBack: ApiCallback<User>) {
        mAppExecutors.diskIO.execute {
            val user = mDataDao?.getUsers(userId)
            mAppExecutors.mainThread.execute {
                apiCallBack.success(user)
            }
        }
    }
}
