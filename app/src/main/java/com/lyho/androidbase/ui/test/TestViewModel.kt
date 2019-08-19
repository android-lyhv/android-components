package com.lyho.androidbase.ui.test

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.lyho.androidbase.model.entities.User
import com.lyho.androidbase.model.network.ApiCallback
import com.lyho.androidbase.model.network.ApiError
import com.lyho.androidbase.model.repository.UserRepository
import com.lyho.androidbase.ui.common.viewmodel.BaseAndroidViewModel

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
class TestViewModel(application: Application, private val userRepository: UserRepository) : BaseAndroidViewModel(application) {
    val mTestLiveData: MutableLiveData<User> = MutableLiveData()
    fun getUser(userId: Int) {
        userRepository.getUserAsync(userId, object : ApiCallback<User>() {
            override fun success(t: User?) {
                mTestLiveData.postValue(t)
            }

            override fun failure(apiError: ApiError?) {
                mErrorLiveData.postValue(apiError)
            }
        })
    }
}