package com.lyho.androidbase.model.repository

import com.lyho.androidbase.model.entities.User
import com.lyho.androidbase.model.network.ApiCallback

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
interface IUserRepository {
    fun getUserAsync(userId: Int, apiCallBack: ApiCallback<User>)
}
