package com.lyhv.mvvm.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lyhv.mvvm.di.Injectable
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    abstract val resLayoutId: Int
    lateinit var binding: T
    abstract fun initViews()
    abstract fun subscribeUI()

    override fun onAttach(context: Context) {
        if (this is Injectable) {
            AndroidSupportInjection.inject(this)
        }
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<T>(inflater, resLayoutId, container, false)
            .apply { binding = this }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribeUI()
    }

    fun popBackStackByCurrentFragmentId(
        currentFragmentId: Int,
        destinationId: Int? = null
    ) {
        if (findNavController().currentDestination?.id == currentFragmentId) {
            destinationId?.let {
                findNavController().popBackStack(destinationId, false)
            } ?: run {
                findNavController().popBackStack()
            }
        }
    }
}
