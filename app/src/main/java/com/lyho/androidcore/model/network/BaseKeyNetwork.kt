package com.lyho.androidcore.model.network

/**
 * Created by lyhv on August 19, 2019
 * Copyright @ est-rouge. All rights reserved
 */
abstract class BaseKeyNetwork(val keyName: String) {
    abstract fun getValue(): String
}