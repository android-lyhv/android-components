package com.lyho.androidbase.model.network

import android.app.Application

/**
 * Created by lyhv on August 19, 2019
 * Copyright @ est-rouge. All rights reserved
 */

object ApplicationConfig {
    private lateinit var application: Application
    fun injectApplication(application: Application) {
        this.application = application
    }
    fun getApplication() = application
}
