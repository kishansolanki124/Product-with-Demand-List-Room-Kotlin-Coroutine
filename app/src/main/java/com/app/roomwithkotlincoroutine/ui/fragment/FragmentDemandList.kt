package com.app.roomwithkotlincoroutine.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.roomwithkotlincoroutine.R
import com.app.roomwithkotlincoroutine.databinding.FragmentDemandListBinding
import com.app.roomwithkotlincoroutine.db.DatabaseBuilder
import com.app.roomwithkotlincoroutine.db.DatabaseHelperImpl
import com.app.roomwithkotlincoroutine.db.pojo.DemandWithProduct
import com.app.roomwithkotlincoroutine.ui.activity.AddDemandActivity
import com.app.roomwithkotlincoroutine.ui.adapter.DemandListAdapter
import com.app.roomwithkotlincoroutine.util.AppConstant
import com.app.roomwithkotlincoroutine.util.ViewModelFactory
import com.app.roomwithkotlincoroutine.util.setRecyclerViewLayoutManager
import com.app.roomwithkotlincoroutine.viewmodel.RoomDBViewModel

class FragmentDemandList : Fragment() {

    private lateinit var binding: FragmentDemandListBinding
    private lateinit var viewModel: RoomDBViewModel
    private lateinit var demandListAdapter: DemandListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDemandListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.Demand)

        binding.fbAdd.setOnClickListener {
            startActivityForResult(
                Intent(requireContext(), AddDemandActivity::class.java),
                AppConstant.ACTIVITY_RESULT_CODE_100
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

        setRecyclerViewLayoutManager(binding.rvDemand)

        demandListAdapter = DemandListAdapter {
            startActivityForResult(
                Intent(requireContext(), AddDemandActivity::class.java).putExtra(
                    AppConstant.ADD_DEMAND,
                    it
                ), AppConstant.ACTIVITY_RESULT_CODE_100
            )
        }
        binding.rvDemand.adapter = demandListAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppConstant.ACTIVITY_RESULT_CODE_100) {
            viewModel.fetchDemandList()
        }
    }
}