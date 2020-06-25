package com.lyhv.component.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    fun <A, B> zipLiveData(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A, B>> {
        return MediatorLiveData<Pair<A, B>>().apply {
            var first: A? = null
            var second: B? = null

            fun update() {
                val localFirst = first
                val localSecond = second
                if (localFirst != null && localSecond != null)
                    this.value = Pair(localFirst, localSecond)
            }

            addSource(a) {
                first = it
                update()
            }
            addSource(b) {
                second = it
                update()
            }
        }
    }

    fun <A, B> zipLiveDataAllowNull(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A?, B?>> {
        return MediatorLiveData<Pair<A?, B?>>().apply {
            var first: A? = null
            var second: B? = null
            var isFirstEmitted = false
            var isSecondEmitted = false

            fun update() {
                val localFirst = first
                val localSecond = second
                if (isFirstEmitted && isSecondEmitted) {
                    this.value = Pair(localFirst, localSecond)
                }
            }

            addSource(a) {
                isFirstEmitted = true
                first = it
                update()
            }
            addSource(b) {
                isSecondEmitted = true
                second = it
                update()
            }
        }
    }

}
