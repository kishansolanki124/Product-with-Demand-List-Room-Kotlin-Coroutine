package com.app.roomwithkotlincoroutine.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.roomwithkotlincoroutine.R
import com.app.roomwithkotlincoroutine.showToast

class FragmentTwoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.showToast("Fragment two")
    }

    override fun onDetach() {
        super.onDetach()
        activity!!.showToast("Fragment two Detach")
    }
}