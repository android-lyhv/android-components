package com.lampurema.androidvpn.model.remote

import android.app.Application
import android.text.TextUtils
import com.lampurema.androidvpn.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Ly Ho V.
 */
class ApiClient private constructor() {
    private var mApplication: Application? = null
    var apiService: ApiService? = null
        private set

    /**
     * @return header appKey
     */
    private fun getHeaderToken(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val token = mApplication?.let { ApiKeyStore.getInstance(it) }?.get(ApiKeyStore.ACCESS_TOKEN_KEY)
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
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }

    fun init(application: Application) {
        mApplication = application
        // initialize OkHttpClient
        val builderApi = OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptorDebug())
            .addInterceptor(getHeaderToken())
            .readTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
        // Config retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.HOST_API)
            .client(builderApi.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    companion object {
        private const val TIME_OUT = 30 * 1000
        private var sApiClient: ApiClient? = null

        fun getInstance(): ApiClient? {
            if (sApiClient == null) {
                sApiClient = ApiClient()
            }
            return sApiClient
        }
    }
}
