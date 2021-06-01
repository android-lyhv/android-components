package com.lyhv.component.di

import com.lyhv.component.di.AppModule.Companion.MOSHI_NAME_COMMON
import com.lyhv.component.core.api.MoshiHelper
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class CoreDataModule {

    @Singleton
    @Provides
    @Named(MOSHI_NAME_COMMON)
    internal fun provideMoshi(): Moshi {
        return MoshiHelper.commonMoshi()
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)
}