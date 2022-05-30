package com.rayliu.lifecycleexplorer.cards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rayliu.lifecycleexplorer.R

class CardsFragment(
    private var callback: FragmentLifecycleCallback? = null
) : Fragment(R.layout.fragment_cards) {

    private var cardId: String = "Not init yet"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val title = requireArguments().getString("title")
        requireNotNull(title) {
            "title is null"
        }
        this.cardId = title

        callback?.onFragmentEventCallback(title, "onAttach()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callback?.onFragmentEventCallback(cardId, "onCreateView()")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callback?.onFragmentEventCallback(cardId, "onViewCreated()")
        retrieveViews(view)
    }

    private fun retrieveViews(view: View) {
        val titleTextView: TextView = view.findViewById(R.id.cards_text_view)
        val cardRootView: FrameLayout = view.findViewById(R.id.cards_root_view)

        val colorRes = requireArguments().getInt("colorRes")
        requireNotNull(cardId) {
            "colorRes is null"
        }
        titleTextView.text = cardId
        cardRootView.setBackgroundColor(ContextCompat.getColor(requireContext(), colorRes))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        callback?.onFragmentEventCallback(cardId, "onActivityCreated()")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        callback?.onFragmentEventCallback(cardId, "onViewStateRestored()")
    }

    override fun onStart() {
        super.onStart()
        callback?.onFragmentEventCallback(cardId, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        callback?.onFragmentEventCallback(cardId, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        callback?.onFragmentEventCallback(cardId, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        callback?.onFragmentEventCallback(cardId, "onPause()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback?.onFragmentEventCallback(cardId, "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        callback?.onFragmentEventCallback(cardId, "onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        callback?.onFragmentEventCallback(cardId, "onDetach()")
        callback = null
    }
}
