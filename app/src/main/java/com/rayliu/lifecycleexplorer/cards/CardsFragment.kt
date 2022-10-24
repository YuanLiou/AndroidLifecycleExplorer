package com.rayliu.lifecycleexplorer.cards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rayliu.lifecycleexplorer.R

class CardsFragment(
    private var callback: FragmentLifecycleCallback? = null
) : Fragment(R.layout.fragment_cards) {

    private var cardId: String = "Not init yet"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val title = requireArguments().getString(TITLE_KEY)
        requireNotNull(title) {
            "title is null"
        }
        this.cardId = title

        sendCallbackEvent(message = "onAttach()")
    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        sendCallbackEvent(message = "onGetLayoutInflater()")
        return super.onGetLayoutInflater(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendCallbackEvent(cardId, "onCreateView()")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendCallbackEvent(message = "onViewCreated()")
        retrieveViews(view)
    }

    private fun retrieveViews(view: View) {
        val titleTextView: TextView = view.findViewById(R.id.cards_text_view)
        val cardRootView: FrameLayout = view.findViewById(R.id.cards_root_view)

        val colorRes = requireArguments().getInt(COLOR_KEY, -1)
        require(colorRes != -1) {
            "colorRes is not set"
        }
        titleTextView.text = cardId
        cardRootView.setBackgroundColor(ContextCompat.getColor(requireContext(), colorRes))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sendCallbackEvent(message = "onActivityCreated()")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        sendCallbackEvent(message = "onViewStateRestored()")
    }

    override fun onStart() {
        super.onStart()
        sendCallbackEvent(message = "onStart()")
    }

    override fun onResume() {
        super.onResume()
        sendCallbackEvent(message = "onResume()")
    }

    override fun onPause() {
        super.onPause()
        sendCallbackEvent(message = "onPause()")
    }

    override fun onStop() {
        super.onStop()
        sendCallbackEvent(message = "onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sendCallbackEvent(message = "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        sendCallbackEvent(message = "onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        sendCallbackEvent(message = "onDetach()")
        callback = null
    }

    fun setFragmentLifecycleCallback(callback: FragmentLifecycleCallback) {
        this.callback = callback
    }

    private fun sendCallbackEvent(id: String = cardId, message: String) {
        callback?.onFragmentEventCallback(id, message)
    }

    companion object {
        val TITLE_KEY = "title"
        val COLOR_KEY = "colorRes"

        fun newInstance(title: String, @ColorRes colorResId: Int): CardsFragment {
            val fragment = CardsFragment().apply {
                arguments = bundleOf(
                    CardsFragment.TITLE_KEY to title,
                    CardsFragment.COLOR_KEY to colorResId
                )
            }
            return fragment
        }
    }
}
