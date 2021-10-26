package com.lyhv.mvvm.core.api

import com.lyhv.mvvm.BuildConfig


interface ApiCommonService {
    companion object {
        const val ENDPOINT = BuildConfig.BASE_API_URL
    }
}