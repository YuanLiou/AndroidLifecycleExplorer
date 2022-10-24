package com.rayliu.lifecycleexplorer.fragment

sealed class FragmentLifecycleViewState {
    data class LifecycleUpdate(
        val id: String = "FragmentLifecycle",
        val message: String = ""
    ) : FragmentLifecycleViewState()
    object RESET : FragmentLifecycleViewState()
}
