package com.lyhv.component.core.livedata

import androidx.lifecycle.MediatorLiveData

class ToggleLiveData : MediatorLiveData<Void>() {
    fun toggle() {
        value = null
    }
}