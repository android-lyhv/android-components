package com.lyhv.component.component

import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.lyhv.component.R
import com.lyhv.component.common.base.BaseFragment
import com.lyhv.component.common.logger
import com.lyhv.component.databinding.HomeFragmentBinding
import com.lyhv.component.component.corountine.downloadFlow
import com.lyhv.component.component.corountine.downloadProcess
import com.lyhv.component.component.corountine.downloadState
import com.lyhv.component.component.corountine.processShareFlowWithBufferDropOldest
import com.lyhv.component.component.corountine.processShareFlowWithBufferSuspend
import com.lyhv.component.component.corountine.processSharedFlow
import com.lyhv.component.component.corountine.shareFlowWithBufferSuspend
import com.lyhv.component.component.corountine.sharedFlowDefault
import com.lyhv.component.component.corountine.sharedFlowWithBufferDropOldest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class MenuFragment : BaseFragment<HomeFragmentBinding>() {
    override val resLayoutId: Int
        get() = R.layout.home_fragment

    override fun initViews() {
        binding.tvCompose.setOnClickListener {
            // TODO
        }
    }

    override fun subscribeUI() {
        stateFlow()
        sharedFlow()
    }

    private fun stateFlow() {
        lifecycleScope.launch {
            downloadProcess()
        }
        /**
         *  State Flow
         */
        downloadState.asLiveData().observe(this, {
            logger<MenuFragment>().debug("State $it")
        })
        lifecycleScope.launch {
            delay(2000)
            downloadState.asLiveData().observe(this@MenuFragment, {
                logger<MenuFragment>().debug("Other State $it")
            })
        }

        /**
         * Normal Flow
         */
        downloadFlow.asLiveData().observe(this, {
            logger<MenuFragment>().debug("Flow $it")
        })
        lifecycleScope.launch {
            delay(2000)
            downloadFlow.asLiveData().observe(this@MenuFragment, {
                logger<MenuFragment>().debug("Other Flow $it")
            })
        }
    }

    /**
     * Management stream state
     */
    private fun sharedFlow() {
        /**
         * Default
         */
        lifecycleScope.launch {
            processSharedFlow()
        }
        sharedFlowDefault.asLiveData().observe(this@MenuFragment, {
            logger<MenuFragment>().debug("sharedFlowDefault $it")
        })
        lifecycleScope.launch {
            delay(2000)
            sharedFlowDefault.asLiveData().observe(this@MenuFragment, {
                logger<MenuFragment>().debug("sharedFlowDefault Flow $it")
            })
        }
        lifecycleScope.launch {
            delay(1000)
            sharedFlowDefault.shareIn(lifecycleScope, SharingStarted.Eagerly).collect {
                logger<MenuFragment>().debug("sharedFlowDefault Shared In $it")
            }
        }
        /**
         * suspend
         */
        lifecycleScope.launch {
            processShareFlowWithBufferSuspend()
        }
        shareFlowWithBufferSuspend.asLiveData().observe(this, {
            logger<MenuFragment>().debug("shareFlowWithBufferSuspend $it")
        })
        lifecycleScope.launch {
            delay(1000)
            shareFlowWithBufferSuspend.asLiveData().observe(this@MenuFragment, {
                logger<MenuFragment>().debug("shareFlowWithBufferSuspend Flow $it")
            })
        }

        /**
         * Old
         */
        lifecycleScope.launch {
            processShareFlowWithBufferDropOldest()
        }
        sharedFlowWithBufferDropOldest.asLiveData().observe(this, {
            logger<MenuFragment>().debug("shareFlowWithBufferDropOld $it")
        })
        lifecycleScope.launch {
            delay(1000)
            sharedFlowWithBufferDropOldest.asLiveData().observe(this@MenuFragment, {
                logger<MenuFragment>().debug("shareFlowWithBufferDropOld Flow $it")
            })
        }
    }

}