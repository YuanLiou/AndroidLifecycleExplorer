package com.rayliu.lifecycleexplorer.utils

import android.app.Activity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.rayliu.lifecycleexplorer.R

fun DrawerLayout.syncMenuWithToolbar(activitiy: Activity, toolbar: Toolbar): ActionBarDrawerToggle {
    val toggle = ActionBarDrawerToggle(activitiy, this, toolbar, R.string.navigtion_drawer_open, R.string.navigtion_drawer_close)
    this.addDrawerListener(toggle)
    toggle.isDrawerIndicatorEnabled = true
    toggle.syncState()
    return toggle
}
