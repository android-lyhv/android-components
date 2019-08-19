package com.lyho.androidbase.ui.common.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.lyho.androidbase.model.network.ApiError

/**
 * Created by Ly Ho V. on October 04, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
abstract class BaseViewModel : ViewModel() {
    protected val mErrorLiveData = MutableLiveData<ApiError>()
    protected val mOnLoadingLiveData = MutableLiveData<Boolean>()
}
