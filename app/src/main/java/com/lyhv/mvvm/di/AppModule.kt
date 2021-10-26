package com.lyhv.mvvm.di

import android.app.Application
import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.lyhv.mvvm.BuildConfig
import com.lyhv.mvvm.common.utils.SPUtils
import com.lyhv.mvvm.core.api.ApiCommonService
import com.lyhv.mvvm.core.data.AppDatabase
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Suppress("unused")
@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {
    companion object {
        private val LIST_PUBLIC_APIS = listOf(
            "products",
            "tokens"
        )

        fun isPublicApi(urlPath: String): Boolean {
            return LIST_PUBLIC_APIS.any { urlPath.contains(it) }
        }

        const val RETROFIT_NAME_COMMON = "retrofit_common"

        const val SERVICE_NAME_COMMON_API = "service_common_api"

        const val OKHTTP_NAME_COMMON = "okhttp_common"

        const val MOSHI_NAME_COMMON = "moshi_common"

        const val INTERCEPTOR_BASIC_AUTH = "interceptor_basic_auth"

        const val INTERCEPTOR_COMMON = "interceptor_common"
    }

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    internal fun provideSPUtils(app: Application, @Named(MOSHI_NAME_COMMON) moshi: Moshi): SPUtils {
        return SPUtils(app, moshi)
    }

    @Singleton
    @Provides
    @Named(RETROFIT_NAME_COMMON)
    fun provideRetrofit(
        @Named(MOSHI_NAME_COMMON) moshi: Moshi,
        @Named(OKHTTP_NAME_COMMON) okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiCommonService.ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    @Named(OKHTTP_NAME_COMMON)
    fun provideOkHttp(
        @Named(INTERCEPTOR_COMMON)
        interceptorCommon: Interceptor,
        @Named(INTERCEPTOR_BASIC_AUTH)
        interceptorBasicAuth: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptorCommon)
            .addInterceptor(interceptorBasicAuth)
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    @Named(INTERCEPTOR_BASIC_AUTH)
    fun provideInterceptorBaseAuth(): Interceptor {
        val credentials: String =
            Credentials.basic(BuildConfig.USER_BASIC_AUTH, BuildConfig.PASSWORD_BASIC_AUTH)
        return Interceptor {
            val requestBuilder = it.request().newBuilder()
                .header("Authorization", credentials)
            it.proceed(requestBuilder.build())
        }
    }

    @Singleton
    @Provides
    @Named(INTERCEPTOR_COMMON)
    fun provideInterceptorCommon(): Interceptor {
        return Interceptor {
            val requestBuilder = it.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("X-HTTP-ServiceToken", BuildConfig.X_HTTP_SERVICETOKEN)
            it.proceed(requestBuilder.build())
        }
    }


    @Singleton
    @Provides
    @Named(SERVICE_NAME_COMMON_API)
    internal fun provideService(@Named(RETROFIT_NAME_COMMON) retrofit: Retrofit): ApiCommonService {
        return retrofit.create(ApiCommonService::class.java)
    }

    //TODO Need to remove fallbackToDestructiveMigration api when release to the production
    @Singleton
    @Provides
    fun provideDb(app: Application, @Named(MOSHI_NAME_COMMON) moshi: Moshi): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DB_NAME)
            .build()
            .apply { AppDatabase.moshi = moshi }
    }

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)
}
