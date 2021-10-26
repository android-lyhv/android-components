package com.lyhv.mvvm.di

import android.app.Application
import com.lyhv.mvvm.common.utils.SPUtils
import com.lyhv.mvvm.ui.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: MyApplication)

    //SpUtils
    fun getSpUtils(): SPUtils
}