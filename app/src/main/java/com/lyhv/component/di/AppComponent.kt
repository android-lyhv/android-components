package com.lyhv.component.di

import android.app.Application
import com.lyhv.component.common.utils.SPUtils
import com.lyhv.component.ui.MyApplication
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