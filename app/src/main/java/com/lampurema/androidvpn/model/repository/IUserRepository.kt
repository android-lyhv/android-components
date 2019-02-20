package com.lampurema.androidvpn.model.repository

import com.lampurema.androidvpn.model.entities.User
import com.lampurema.androidvpn.model.remote.ApiCallback

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
interface IUserRepository {
    fun getUser(userId: Int, apiCallBack: ApiCallback<User>)
}
