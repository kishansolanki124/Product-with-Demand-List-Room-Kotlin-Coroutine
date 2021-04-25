package com.app.roomwithkotlincoroutine.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.roomwithkotlincoroutine.databinding.FragmentTwoBinding
import com.app.roomwithkotlincoroutine.db.DatabaseBuilder
import com.app.roomwithkotlincoroutine.db.DatabaseHelperImpl
import com.app.roomwithkotlincoroutine.db.pojo.DemandWithProduct
import com.app.roomwithkotlincoroutine.ui.activity.AddDemandActivity
import com.app.roomwithkotlincoroutine.ui.adapter.DemandListAdapter
import com.app.roomwithkotlincoroutine.util.ViewModelFactory
import com.app.roomwithkotlincoroutine.viewmodel.RoomDBViewModel

class FragmentTwoFragment : Fragment() {

    private lateinit var binding: FragmentTwoBinding
    private lateinit var viewModel: RoomDBViewModel
    private lateinit var demandListAdapter: DemandListAdapter
    private lateinit var layoutManager: LinearLayoutManager

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
            startActivityForResult(
                Intent(requireContext(), AddDemandActivity::class.java), 100
            )
        }

        viewModel = ViewModelProvider(
            this, ViewModelFactory(
                DatabaseHelperImpl(
                    DatabaseBuilder.getInstance(requireContext())
                )
            )
        ).get(RoomDBViewModel::class.java)

        viewModel.fetchDemandList()

        viewModel.getDemandList().observe(requireActivity(), {
            demandListAdapter.setItem(it as ArrayList<DemandWithProduct>)
        })


        //recyclerview
        layoutManager = LinearLayoutManager(requireContext())
        binding.rvVatanNuGham.layoutManager = layoutManager

        demandListAdapter = DemandListAdapter {
            startActivityForResult(
                Intent(requireContext(), AddDemandActivity::class.java).putExtra(
                    "demand",
                    it
                ), 100
            )
        }
        binding.rvVatanNuGham.adapter = demandListAdapter
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 100) {
            //second check whether they are authorized to download
            viewModel.fetchDemandList()
        }
    }
}