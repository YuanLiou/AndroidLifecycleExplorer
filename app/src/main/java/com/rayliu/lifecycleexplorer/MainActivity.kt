package com.rayliu.lifecycleexplorer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rayliu.lifecycleexplorer.cards.CardFragmentFactory
import com.rayliu.lifecycleexplorer.cards.CardsFragment
import com.rayliu.lifecycleexplorer.cards.CardsFragment.Companion.COLOR_KEY
import com.rayliu.lifecycleexplorer.cards.FragmentLifecycleCallback
import com.rayliu.lifecycleexplorer.cards.LifecycleLog
import com.rayliu.lifecycleexplorer.databinding.ActivityMainBinding
import com.rayliu.lifecycleexplorer.fragment.FragmentLifecycleViewModel
import com.rayliu.lifecycleexplorer.fragment.FragmentLifecycleViewState
import com.rayliu.lifecycleexplorer.utils.CardGenerators
import com.rayliu.lifecycleexplorer.utils.DrawerRouter
import com.rayliu.lifecycleexplorer.utils.syncMenuWithToolbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener, FragmentLifecycleCallback {

    private val viewModel: FragmentLifecycleViewModel by viewModels()

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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { viewState ->
                    when (viewState) {
                        is FragmentLifecycleViewState.LifecycleUpdate -> {
                            printLog(viewState.id, viewState.message)
                        }
                        FragmentLifecycleViewState.RESET -> {
                            cleanLogTexts()
                        }
                    }
                }
            }
        }

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
        val id = CardGenerators.generateFragmentTag(currentIndex++) ?: return
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(
            R.id.main_fragment_container_view,
            CardsFragment::class.java,
            bundleOf(
                CardsFragment.TITLE_KEY to id,
                CardsFragment.COLOR_KEY to CardGenerators.generateRandomColorRes()
            )
        )
        previousId?.let { ft.addToBackStack(it) }
        ft.commit()

        previousId = id
    }

    override fun onNavigateUp(): Boolean {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            return true
        }
        return super.onNavigateUp()
    }

    //region Callback Actions
    private fun cleanLogTexts() {
        // TODO: clean Logs
    }

    private fun printLog(id: String, message: String) {
        // TODO: print Logs
        Log.w("MainActivity", "id = $id, message = $message")
    }
    //endregion

    //region FragmentLifecycleCallback
    override fun onFragmentEventCallback(lifecycleLog: LifecycleLog) {
        viewModel.updateLifecycleLog(lifecycleLog)
    }
    //endregion
}
