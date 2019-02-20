package com.lampurema.androidvpn.ui.test

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.lampurema.androidvpn.model.entities.User
import com.lampurema.androidvpn.model.remote.ApiCallback
import com.lampurema.androidvpn.model.repository.UserRepository

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
class TestViewModel(application: Application, private val userRepository: UserRepository) : AndroidViewModel(application) {
    val mTestLiveData: MutableLiveData<User> = MutableLiveData()
    fun getUser(userId: Int) {
        userRepository.getUser(userId, object : ApiCallback<User>() {
            override fun success(t: User?) {
                mTestLiveData.postValue(t)
            }
        })
    }
}