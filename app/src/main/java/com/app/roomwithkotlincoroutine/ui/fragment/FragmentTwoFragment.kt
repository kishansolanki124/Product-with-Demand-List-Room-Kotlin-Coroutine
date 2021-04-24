package com.app.roomwithkotlincoroutine.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.roomwithkotlincoroutine.databinding.FragmentTwoBinding
import com.app.roomwithkotlincoroutine.showToast
import com.app.roomwithkotlincoroutine.ui.activity.AddDemandActivity

class FragmentTwoFragment : Fragment() {

    private lateinit var binding: FragmentTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fbAdd.setOnClickListener {
            startActivity(Intent(requireContext(), AddDemandActivity::class.java))
        }
    }

    override fun onDetach() {
        super.onDetach()
        activity!!.showToast("Fragment two Detach")
    }
}