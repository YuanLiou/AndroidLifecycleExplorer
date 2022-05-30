package com.rayliu.lifecycleexplorer.cards

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rayliu.lifecycleexplorer.R

class CardsFragment : Fragment(R.layout.fragment_cards) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrieveViews(view)
    }

    private fun retrieveViews(view: View) {
        val titleTextView: TextView = view.findViewById(R.id.cards_text_view)
        val cardRootView: FrameLayout = view.findViewById(R.id.cards_root_view)

        val title = requireArguments().getString("title")
        requireNotNull(title) {
            "title is null"
        }
        val colorRes = requireArguments().getInt("colorRes")
        requireNotNull(title) {
            "colorRes is null"
        }
        titleTextView.text = title
        cardRootView.setBackgroundColor(ContextCompat.getColor(requireContext(), colorRes))
    }
}