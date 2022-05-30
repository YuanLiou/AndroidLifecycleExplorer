package com.rayliu.lifecycleexplorer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import com.rayliu.lifecycleexplorer.cards.CardFragmentFactory
import com.rayliu.lifecycleexplorer.cards.CardsFragment
import com.rayliu.lifecycleexplorer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainAppBarToolbar)

        supportFragmentManager.fragmentFactory = CardFragmentFactory()
        binding.mainFragmentReplaceButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_fragment_container_view, CardsFragment::class.java, bundleOf(
            "title" to "Hello Fragment A",
            "colorRes" to R.color.charm
        ))
        ft.commit()
    }
}