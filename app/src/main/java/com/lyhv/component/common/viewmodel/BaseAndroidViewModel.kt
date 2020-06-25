package com.lyhv.component.common.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lyhv.component.model.network.ApiError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

/**
 * Created by Ly Ho V. on October 04, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope by ViewModelScope() {
    protected val mErrorLiveData = MutableLiveData<ApiError>()
    protected val mOnLoadingLiveData = MutableLiveData<Boolean>()
    override fun onCleared() {
        super.onCleared()
        cancel()
    }
}
