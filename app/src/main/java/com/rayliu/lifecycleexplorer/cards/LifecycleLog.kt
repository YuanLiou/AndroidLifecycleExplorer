package com.rayliu.lifecycleexplorer.cards

import com.rayliu.lifecycleexplorer.R

data class LifecycleLog(
    val id: String,
    val message: String,
    val colorBackgroundResId: Int = R.color.white
)
