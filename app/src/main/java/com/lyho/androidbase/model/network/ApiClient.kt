package com.lyho.androidbase.model.network

import android.text.TextUtils
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.lyho.androidbase.BuildConfig
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

    private lateinit var mRetrofit: Retrofit
    private fun setupConfig(networkConfig: NetworkConfig) {
        mNetworkConfig = networkConfig
        // initialize OkHttpClient
        val okkHttpConfig = OkHttpClient.Builder()
                .addInterceptor(getHttpLoggingInterceptorDebug())
                .addInterceptor(getHeaderToken())
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

    fun <T : BaseApiService> createService(service: Class<T>): T {
        return mRetrofit.create(service)
    }

    companion object {
        private lateinit var sApiClient: ApiClient
        fun getInstance(
                networkConfig: NetworkConfig
        ): ApiClient {
            if (sApiClient == null) {
                sApiClient = ApiClient(networkConfig)
            }
            return sApiClient
        }
    }

    /**
     * @return header appKey
     */
    private fun getHeaderToken(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val token = ApiKeyStore.getInstance(ApplicationConfig.getApplication())[ApiKeyStore.ACCESS_TOKEN_KEY]
            if (TextUtils.isEmpty(token)) {
                return@Interceptor chain.proceed(original.newBuilder().build())
            } else {
                val request = original.newBuilder()
                        .header("token", token)
                        .method(original.method(), original.body())
                        .build()
                return@Interceptor chain.proceed(request)
            }
        }
    }

    /**
     * @return Log debug
     */
    private fun getHttpLoggingInterceptorDebug(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }
}
