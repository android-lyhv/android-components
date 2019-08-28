package com.lyho.androidcore.model.network

import com.lyho.androidcore.model.entities.User
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by lyhv on August 19, 2019
 * Copyright @ est-rouge. All rights reserved
 */
interface ApiService {
    @GET("v1/user/{id}")
    fun getUser(@Path("id") id: Int): Call<User>

    @GET("v1/user/{id}")
    fun getUserDeferred(@Path("id") id: Int): Deferred<User>

    @GET("v1/user/{id}")
    fun getUserObservable(@Path("id") id: Int): Observable<User>
}
