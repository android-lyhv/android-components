package com.lyhv.component.common.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lyhv.component.model.network.ApiError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

/**
 * Created by Ly Ho V. on October 04, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
abstract class BaseViewModel : ViewModel(), CoroutineScope by ViewModelScope() {
    protected val mErrorLiveData = MutableLiveData<ApiError>()
    protected val mOnLoadingLiveData = MutableLiveData<Boolean>()
    override fun onCleared() {
        super.onCleared()
        cancel()
    }
}
