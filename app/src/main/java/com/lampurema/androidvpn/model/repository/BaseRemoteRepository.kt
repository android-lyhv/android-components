import com.lampurema.androidvpn.model.remote.ApiClient
import com.lampurema.androidvpn.model.remote.ApiService

/**
 * Created by Ly Ho V. on October 29, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
abstract class BaseRemoteRepository {
    protected var apiService: ApiService? = ApiClient.instance.apiService
}
