package com.rayliu.lifecycleexplorer.demoviewpager2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rayliu.lifecycleexplorer.R
import com.rayliu.lifecycleexplorer.cards.CardsFragment
import com.rayliu.lifecycleexplorer.cards.FragmentLifecycleCallback
import com.rayliu.lifecycleexplorer.databinding.ActivityViewpager2Binding
import com.rayliu.lifecycleexplorer.utils.CardGenerators
import com.rayliu.lifecycleexplorer.utils.DrawerRouter
import com.rayliu.lifecycleexplorer.utils.printLogs
import com.rayliu.lifecycleexplorer.utils.syncMenuWithToolbar

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
        syncMenuWithToolbar()
        setupNavigationMenu()
        binding.viewpager2CleanUp.setOnClickListener(this)

        cleanUpText()
        
        binding.viewpager2ContainerView.doOnLayout {
            setupViewPager()
        }
    }

    private fun syncMenuWithToolbar() {
        binding.viewpager2DrawerLayout.syncMenuWithToolbar(this, binding.viewpager2AppBarToolbar)
    }

    private fun setupNavigationMenu() {
        router.attachToDefaultRoute(binding.viewpager2Navigation, R.id.action_viewpager2_lifecycle)
    }

    private fun setupViewPager() {
        binding.viewpager2ContainerView.adapter = ViewPagerStatePagerAdapter(
            fragmentManager = supportFragmentManager,
            lifecycleOwner = this,
            maxSize = 5,
            callback = this
        )
    }

    private fun cleanUpText() {
        binding.viewpager2ResponseTextview.text = "FragmentStateAdapter(fm, lifecycle)"
    }

    //region FragmentLifecycleCallback
    override fun onFragmentEventCallback(id: String, message: String) {
        binding.viewpager2ResponseTextview.printLogs(id, message)
    }
    //endregion

    //region View.OnClickListener
    override fun onClick(view: View) {
        if (view.id == R.id.viewpager2_clean_up) {
            cleanUpText()
        }
    }
    //endregion

    class ViewPagerStatePagerAdapter(
        fragmentManager: FragmentManager,
        lifecycleOwner: LifecycleOwner,
        private val maxSize: Int,
        private val callback: FragmentLifecycleCallback
    ) : FragmentStateAdapter(fragmentManager, lifecycleOwner.lifecycle) {
        override fun getItemCount(): Int {
            return maxSize
        }

        override fun createFragment(position: Int): Fragment {
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