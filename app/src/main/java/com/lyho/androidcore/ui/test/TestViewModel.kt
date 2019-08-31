package com.lyho.androidcore.ui.test

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lyho.androidcore.model.entities.User
import com.lyho.androidcore.model.network.ApiError
import com.lyho.androidcore.model.network.ResultCallBack
import com.lyho.androidcore.model.repository.UserRepository
import com.lyho.androidcore.ui.common.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.cancel

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
class TestViewModel(application: Application, private val userRepository: UserRepository) :
    BaseAndroidViewModel(application) {
    val mTestLiveData: MutableLiveData<User> = MutableLiveData()
    fun getUser(userId: Int) {
        userRepository.getUserAsync(userId, object : ResultCallBack<User>() {
            override fun success(t: User?) {
                mTestLiveData.postValue(t)
            }

            override fun failure(apiError: ApiError?) {
                mErrorLiveData.postValue(apiError)
            }
        })
    }
}