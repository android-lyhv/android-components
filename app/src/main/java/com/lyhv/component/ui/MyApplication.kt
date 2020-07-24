package com.lyhv.component.ui

import android.app.Application
import com.facebook.stetho.Stetho
import com.lyhv.component.BuildConfig
import com.lyhv.component.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)

        // Enable Debugging for Kotlin Coroutines
        System.setProperty("kotlinx.coroutines.debug", if (BuildConfig.DEBUG) "on" else "off")

        DaggerAppComponent.builder().application(this)
            .build()
            .inject(this)
    }

    override fun androidInjector() = dispatchingAndroidInjector
}
