package com.rayliu.lifecycleexplorer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.rayliu.lifecycleexplorer.cards.CardFragmentFactory
import com.rayliu.lifecycleexplorer.cards.CardsFragment
import com.rayliu.lifecycleexplorer.cards.FragmentLifecycleCallback
import com.rayliu.lifecycleexplorer.databinding.ActivityMainBinding
import com.rayliu.lifecycleexplorer.utils.CardGenerators

class MainActivity : AppCompatActivity(), View.OnClickListener, FragmentLifecycleCallback {

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

        supportFragmentManager.fragmentFactory = CardFragmentFactory(this)

        binding.mainFragmentReplaceButton.setOnClickListener(this)
        binding.mainFragmentCleanButton.setOnClickListener(this)
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
                "title" to id,
                "colorRes" to CardGenerators.generateRandomColorRes()
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
        val messageTextView = binding.mainResponseTextview
        val currentLogMessage = messageTextView.text.toString()
        if (currentLogMessage.isEmpty()) {
            messageTextView.text = id + ": " + message
        } else {
            messageTextView.text = currentLogMessage + "\n" + id + ": " + message
        }
    }

    //region FragmentLifecycleCallback
    override fun onFragmentEventCallback(id: String, message: String) {
        printLog(id, message)
    }
    //endregion
}
