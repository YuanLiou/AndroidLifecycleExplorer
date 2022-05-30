package com.rayliu.lifecycleexplorer.cards

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class CardFragmentFactory(
    private val callback: FragmentLifecycleCallback
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            CardsFragment::class.java.name -> CardsFragment(callback)
            else -> super.instantiate(classLoader, className)
        }
    }
}
