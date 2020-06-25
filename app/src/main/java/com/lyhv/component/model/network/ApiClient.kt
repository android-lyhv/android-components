package com.lyhv.component.model.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.lyhv.component.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Ly Ho V.
 */
class ApiClient private constructor(var mNetworkConfig: NetworkConfig) {
    init {
        setupConfig(mNetworkConfig)
    }

    lateinit var mRetrofit: Retrofit
        private set

    private fun setupConfig(networkConfig: NetworkConfig) {
        mNetworkConfig = networkConfig
        // initialize OkHttpClient
        val okkHttpConfig = OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptorDebug())
            .addInterceptor(applyHeaderKeys())
            .readTimeout(networkConfig.timeOut, TimeUnit.MILLISECONDS)
            .writeTimeout(networkConfig.timeOut, TimeUnit.MILLISECONDS)
            .connectTimeout(networkConfig.timeOut, TimeUnit.MILLISECONDS)
            .build()
        // Config retrofit
        mRetrofit = Retrofit.Builder()
            .baseUrl(networkConfig.hostUrl)
            .client(okkHttpConfig)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .build()
    }

    fun <T> createService(service: Class<T>): T {
        return mRetrofit.create(service)
    }

    companion object {
        fun newInstance(
            networkConfig: NetworkConfig
        ): ApiClient {
            return ApiClient(networkConfig)
        }
    }

    /**
     * @return header appKey
     */
    private fun applyHeaderKeys(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val keyNetworks = mNetworkConfig.keyNetworks
            if (keyNetworks.isEmpty()) {
                return@Interceptor chain.proceed(original.newBuilder().build())
            } else {
                val request = original.newBuilder()
                keyNetworks.forEach {
                    request.header(it.keyName, it.getValue())
                }
                request.method(original.method(), original.body())
                return@Interceptor chain.proceed(request.build())
            }
        }
    }

    /**
     * @return Log debug
     */
    private fun getHttpLoggingInterceptorDebug(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }
}
