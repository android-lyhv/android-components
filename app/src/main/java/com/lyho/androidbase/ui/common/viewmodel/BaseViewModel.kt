import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by Ly Ho V. on October 04, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
abstract class BaseViewModel(val onViewModelNotify: OnViewModelNotify?) : ViewModel() {
    /**
     * Subscribe LiveData for notification
     */
    abstract fun subscribeLiveData(lifecycleOwner: LifecycleOwner)

    val mFailureLiveData = MutableLiveData<String>()
}
