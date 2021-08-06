package com.lyhv.component.core.livedata

import androidx.lifecycle.MediatorLiveData

class ToggleLiveData : MediatorLiveData<Any>() {
    fun toggle() {
        value = Any()
    }
}