package com.lyhv.component.core.api

import com.lyhv.component.BuildConfig


interface ApiCommonService {
    companion object {
        const val ENDPOINT = BuildConfig.BASE_API_URL
    }
}