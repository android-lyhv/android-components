package com.lyhv.component.model.repository

import androidx.lifecycle.LiveData
import com.lyhv.component.model.entities.Result
import com.lyhv.component.model.entities.User
import com.lyhv.component.model.network.ResultCallBack

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
interface IUserRepository {
    fun getUserAsync(userId: Int, apiCallBack: ResultCallBack<User>)
    fun getUserLiveData(userId: Int): LiveData<User>
    suspend fun getUser(userId: Int): Result<User>
}
