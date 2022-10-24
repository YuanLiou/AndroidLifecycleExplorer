package com.rayliu.lifecycleexplorer.demoviewpager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.rayliu.lifecycleexplorer.R
import com.rayliu.lifecycleexplorer.cards.CardsFragment
import com.rayliu.lifecycleexplorer.cards.FragmentLifecycleCallback
import com.rayliu.lifecycleexplorer.cards.LifecycleLog
import com.rayliu.lifecycleexplorer.databinding.ActivityViewpagerBinding
import com.rayliu.lifecycleexplorer.utils.CardGenerators
import com.rayliu.lifecycleexplorer.utils.DrawerRouter
import com.rayliu.lifecycleexplorer.utils.printLogs
import com.rayliu.lifecycleexplorer.utils.syncMenuWithToolbar

class ViewPagerActivity : AppCompatActivity(), View.OnClickListener, FragmentLifecycleCallback {

    private val router: DrawerRouter by lazy(LazyThreadSafetyMode.NONE) {
        DrawerRouter(this)
    }

    private var _binding: ActivityViewpagerBinding? = null
    private val binding: ActivityViewpagerBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityViewpagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.viewpagerAppBarToolbar)
        syncMenuWithToolbar()
        setupNavigationMenu()
        setupViewPager()

        cleanUpText()

        binding.viewpagerCleanUp.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun syncMenuWithToolbar() {
        binding.viewpagerDrawerLayout.syncMenuWithToolbar(this, binding.viewpagerAppBarToolbar)
    }

    private fun setupNavigationMenu() {
        router.attachToDefaultRoute(binding.viewpagerNavigation, R.id.action_viewpager_lifecycle)
    }

    private fun setupViewPager() {
        binding.viewpagerContainerView.adapter = ViewPagerStatePagerAdapter(
            fragmentManager = supportFragmentManager,
            maxSize = 5,
            callback = this
        )
    }

    private fun cleanUpText() {
        binding.viewpagerResponseTextview.text = "FragmentStatePagerAdapter: BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT"
    }

    //region FragmentLifecycleCallback
    override fun onFragmentEventCallback(lifecycleLog: LifecycleLog) {
        binding.viewpagerResponseTextview.printLogs(lifecycleLog.id, lifecycleLog.message)
    }
    //endregion

    //region View.OnClickListener
    override fun onClick(view: View) {
        if (view.id == R.id.viewpager_clean_up) {
            cleanUpText()
        }
    }
    //endregion

    class ViewPagerStatePagerAdapter(
        fragmentManager: FragmentManager,
        private val maxSize: Int,
        private val callback: FragmentLifecycleCallback
    ) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int {
            return maxSize
        }

        override fun getItem(position: Int): Fragment {
            val title = CardGenerators.generateFragmentTag(position) ?: "NA"
            val fragment = CardsFragment.newInstance(
                title = title,
                colorResId = CardGenerators.generateRandomColorRes()
            ).also {
                it.setFragmentLifecycleCallback(callback)
            }
            return fragment
        }
    }
}
