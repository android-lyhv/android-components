package com.lyho.androidcore.model.repository

import androidx.lifecycle.LiveData
import com.lyho.androidcore.model.entities.User
import com.lyho.androidcore.model.network.ApiCallback

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
interface IUserRepository {
    fun getUserAsync(userId: Int, apiCallBack: ApiCallback<User>)
    fun getUserLiveData(userId: Int): LiveData<User>
}
