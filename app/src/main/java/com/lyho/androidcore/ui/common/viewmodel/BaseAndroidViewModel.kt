package com.lyho.androidcore.ui.common.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lyho.androidcore.model.network.ApiError

/**
 * Created by Ly Ho V. on October 04, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {
    protected val mErrorLiveData = MutableLiveData<ApiError>()
    protected val mOnLoadingLiveData = MutableLiveData<Boolean>()
}
