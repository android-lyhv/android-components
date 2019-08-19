package com.lyho.androidbase.model.network

/**
 * Created by lyhv on August 19, 2019
 * Copyright @ est-rouge. All rights reserved
 */
abstract class BaseNetworkConfig {
    lateinit var hostUrl: String
    var userAgent: String = ""
    var timeOut: Long = 30 * 1000
}

