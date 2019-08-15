import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData

/**
 * Created by Ly Ho V. on October 04, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
abstract class BaseAndroidViewModel(application: Application, val onViewModelNotify: OnViewModelNotify?) :
    AndroidViewModel(application) {
    /**
     * Subscribe LiveData for notification
     */
    abstract fun subscribeLiveData(lifecycleOwner: LifecycleOwner)

    val mFailureLiveData = MutableLiveData<String>()
}
