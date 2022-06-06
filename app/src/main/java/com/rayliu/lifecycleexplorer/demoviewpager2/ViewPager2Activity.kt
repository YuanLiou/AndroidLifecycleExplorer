package com.rayliu.lifecycleexplorer.demoviewpager2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.rayliu.lifecycleexplorer.R
import com.rayliu.lifecycleexplorer.cards.CardsFragment
import com.rayliu.lifecycleexplorer.cards.FragmentLifecycleCallback
import com.rayliu.lifecycleexplorer.databinding.ActivityViewpager2Binding
import com.rayliu.lifecycleexplorer.utils.CardGenerators
import com.rayliu.lifecycleexplorer.utils.DrawerRouter
import com.rayliu.lifecycleexplorer.utils.printLogs

class ViewPager2Activity : AppCompatActivity(), View.OnClickListener, FragmentLifecycleCallback {

    private val router: DrawerRouter by lazy(LazyThreadSafetyMode.NONE) {
        DrawerRouter(this)
    }

    private var _binding: ActivityViewpager2Binding? = null
    private val binding: ActivityViewpager2Binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityViewpager2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.viewpager2AppBarToolbar)
        setupNavigationMenu()
//        setupViewPager()
//
//        cleanUpText()
//
//        binding.viewpagerCleanUp.setOnClickListener(this)
    }

    private fun setupNavigationMenu() {
        router.attachToDefaultRoute(binding.viewpager2Navigation, R.id.action_viewpager2_lifecycle)
    }

    private fun setupViewPager() {
        binding.viewpager2ContainerView.adapter = ViewPagerStatePagerAdapter(
            fragmentManager = supportFragmentManager,
            maxSize = 5,
            callback = this
        )
    }

    private fun cleanUpText() {
        binding.viewpager2ResponseTextview.text = "FragmentStatePagerAdapter: BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT"
    }

    //region FragmentLifecycleCallback
    override fun onFragmentEventCallback(id: String, message: String) {
        binding.viewpager2ResponseTextview.printLogs(id, message)
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