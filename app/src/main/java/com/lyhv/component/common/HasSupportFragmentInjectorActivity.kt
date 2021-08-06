package com.lyhv.component.common

import androidx.appcompat.app.AppCompatActivity
import com.lyhv.component.di.Injectable
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class HasSupportFragmentInjectorActivity : AppCompatActivity(), HasAndroidInjector, Injectable {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = fragmentInjector
}
