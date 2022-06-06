package com.rayliu.lifecycleexplorer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.rayliu.lifecycleexplorer.cards.CardFragmentFactory
import com.rayliu.lifecycleexplorer.cards.CardsFragment
import com.rayliu.lifecycleexplorer.cards.CardsFragment.Companion.COLOR_KEY
import com.rayliu.lifecycleexplorer.cards.FragmentLifecycleCallback
import com.rayliu.lifecycleexplorer.databinding.ActivityMainBinding
import com.rayliu.lifecycleexplorer.utils.CardGenerators
import com.rayliu.lifecycleexplorer.utils.DrawerRouter
import com.rayliu.lifecycleexplorer.utils.printLogs
import com.rayliu.lifecycleexplorer.utils.syncMenuWithToolbar

class MainActivity : AppCompatActivity(), View.OnClickListener, FragmentLifecycleCallback {

    private val router: DrawerRouter by lazy(LazyThreadSafetyMode.NONE) {
        DrawerRouter(this)
    }

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    private var currentIndex = 0
    private var previousId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainAppBarToolbar)
        syncMenuWithToolbar()

        supportFragmentManager.fragmentFactory = CardFragmentFactory(this)

        binding.mainFragmentReplaceButton.setOnClickListener(this)
        binding.mainFragmentCleanButton.setOnClickListener(this)
        setupNavigationMenu()
    }

    private fun syncMenuWithToolbar() {
        binding.mainDrawerLayout.syncMenuWithToolbar(this, binding.mainAppBarToolbar)
    }

    private fun setupNavigationMenu() {
        router.attachToDefaultRoute(binding.mainPageNavigation, R.id.action_fragment_lifecycle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.main_fragment_replace_button -> {
                populateNewCards()
            }
            R.id.main_fragment_clean_button -> {
                cleanLogTexts()
            }
        }
    }

    private fun populateNewCards() {
        val id = CardGenerators.generateFragmentTag(currentIndex++)
        if (id == null) {
            return
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(
            R.id.main_fragment_container_view, CardsFragment::class.java,
            bundleOf(
                CardsFragment.TITLE_KEY to id,
                CardsFragment.COLOR_KEY to CardGenerators.generateRandomColorRes()
            )
        )
        previousId?.let { ft.addToBackStack(it) }
        ft.commit()

        previousId = id
    }

    private fun cleanLogTexts() {
        binding.mainResponseTextview.text = ""
    }

    override fun onNavigateUp(): Boolean {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            return true
        }
        return super.onNavigateUp()
    }

    private fun printLog(id: String, message: String) {
        binding.mainResponseTextview.printLogs(id, message)
    }

    //region FragmentLifecycleCallback
    override fun onFragmentEventCallback(id: String, message: String) {
        printLog(id, message)
    }
    //endregion
}
