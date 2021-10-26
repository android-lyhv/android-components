package com.lyhv.mvvm.di

import android.app.Application

object AppInjector {
    private lateinit var appComponent: AppComponent
    fun init(application: Application) {
        appComponent = DaggerAppComponent.builder().application(application)
            .build()
    }

    fun getAppComponent() = appComponent
}