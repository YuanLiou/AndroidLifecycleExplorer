package com.rayliu.lifecycleexplorer.utils

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.navigation.NavigationView
import com.rayliu.lifecycleexplorer.MainActivity
import com.rayliu.lifecycleexplorer.R

class DrawerRouter(
    private val currentActivity: Activity
) {

    fun attachToDefaultRoute(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_fragment_lifecycle -> {
                        val intent = Intent(currentActivity, MainActivity::class.java)
                        currentActivity.startActivity(intent)
                        return true
                    }
                }
                return false
            }
        })
    }
}