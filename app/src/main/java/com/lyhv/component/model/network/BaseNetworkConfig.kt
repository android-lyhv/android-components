package com.lyhv.component.model.network

/**
 * Created by lyhv on August 19, 2019
 * Copyright @ est-rouge. All rights reserved
 */
abstract class BaseNetworkConfig {
    var hostUrl: String = "http://apt.test.com"
    var userAgent: String = ""
    var timeOut: Long = 30 * 1000
    val keyNetworks = ArrayList<KeyNetwork>()
}

