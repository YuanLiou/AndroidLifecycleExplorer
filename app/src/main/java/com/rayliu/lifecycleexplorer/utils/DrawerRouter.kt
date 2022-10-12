package com.rayliu.lifecycleexplorer.utils

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import androidx.annotation.IdRes
import com.google.android.material.navigation.NavigationView
import com.rayliu.lifecycleexplorer.MainActivity
import com.rayliu.lifecycleexplorer.R
import com.rayliu.lifecycleexplorer.demoviewpager.ViewPagerActivity
import com.rayliu.lifecycleexplorer.demoviewpager2.ViewPager2Activity

class DrawerRouter(
    private val currentActivity: Activity
) {

    fun attachToDefaultRoute(navigationView: NavigationView, @IdRes disableId: Int? = null) {
        navigationView.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                if (item.itemId == disableId) {
                    return false
                }

                when (item.itemId) {
                    R.id.action_fragment_lifecycle -> {
                        val intent = Intent(currentActivity, MainActivity::class.java)
                        currentActivity.startActivity(intent)
                        return true
                    }
                    R.id.action_viewpager_lifecycle -> {
                        val intent = Intent(currentActivity, ViewPagerActivity::class.java)
                        currentActivity.startActivity(intent)
                        return true
                    }
                    R.id.action_viewpager2_lifecycle -> {
                        val intent = Intent(currentActivity, ViewPager2Activity::class.java)
                        currentActivity.startActivity(intent)
                        return true
                    }
                }

                return false
            }
        })
    }
}
