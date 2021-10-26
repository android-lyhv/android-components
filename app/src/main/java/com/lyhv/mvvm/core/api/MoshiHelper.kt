package com.lyhv.mvvm.core.api

import com.squareup.moshi.Moshi

object MoshiHelper {
    fun commonMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }
}