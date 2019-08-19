package com.lyho.androidbase.model.network

import android.app.Application

/**
 * Created by lyhv on August 19, 2019
 * Copyright @ est-rouge. All rights reserved
 */
interface INetworkConfig {
    fun getHostUrl(): String
    fun getApplicationContext(): Application
    fun getTimeOut(): Long
    fun getUserAgent(): String
}
