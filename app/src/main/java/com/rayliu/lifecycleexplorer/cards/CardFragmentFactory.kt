package com.rayliu.lifecycleexplorer.cards

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class CardFragmentFactory() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            CardsFragment::class.java.name -> CardsFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}
