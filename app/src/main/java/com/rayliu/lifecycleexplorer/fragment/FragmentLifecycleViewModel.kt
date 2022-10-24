package com.rayliu.lifecycleexplorer.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayliu.lifecycleexplorer.cards.LifecycleLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FragmentLifecycleViewModel : ViewModel() {

    private val _lifecycleUpdateEvent =
        MutableStateFlow<FragmentLifecycleViewState>(FragmentLifecycleViewState.RESET)
    val uiState: StateFlow<FragmentLifecycleViewState>
        get() = _lifecycleUpdateEvent.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = FragmentLifecycleViewState.RESET
        )

    fun updateLifecycleLog(lifecycleLog: LifecycleLog) {
        _lifecycleUpdateEvent.value = FragmentLifecycleViewState.LifecycleUpdate(
            lifecycleLog.id,
            lifecycleLog.message
        )
    }
}
