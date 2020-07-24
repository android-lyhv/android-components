package com.lyhv.component.common

import com.lyhv.component.common.base.BaseActivity
import com.lyhv.component.di.Injectable
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class HasSupportFragmentInjectorActivity : BaseActivity(), HasAndroidInjector, Injectable {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = fragmentInjector
}
