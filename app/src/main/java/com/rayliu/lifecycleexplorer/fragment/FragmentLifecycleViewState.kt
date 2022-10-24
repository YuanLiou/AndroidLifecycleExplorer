package com.rayliu.lifecycleexplorer.fragment

import com.rayliu.lifecycleexplorer.cards.LifecycleLog

sealed class FragmentLifecycleViewState {
    data class LifecycleUpdate(val logs: List<LifecycleLog>) : FragmentLifecycleViewState()
    object RESET : FragmentLifecycleViewState()
}
