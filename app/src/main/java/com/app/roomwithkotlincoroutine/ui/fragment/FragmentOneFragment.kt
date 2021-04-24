package com.app.roomwithkotlincoroutine.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.roomwithkotlincoroutine.databinding.FragmentNewsHomeBinding
import com.app.roomwithkotlincoroutine.db.DatabaseBuilder
import com.app.roomwithkotlincoroutine.db.DatabaseHelperImpl
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon
import com.app.roomwithkotlincoroutine.showToast
import com.app.roomwithkotlincoroutine.ui.activity.AddProductActivity
import com.app.roomwithkotlincoroutine.ui.adapter.ProductListAdapter
import com.app.roomwithkotlincoroutine.util.ViewModelFactory
import com.app.roomwithkotlincoroutine.viewmodel.RoomDBViewModel

class FragmentOneFragment : Fragment() {

    private lateinit var binding: FragmentNewsHomeBinding

    private lateinit var viewModel: RoomDBViewModel
    private lateinit var vatanNuGhamAdapter: ProductListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fbAdd.setOnClickListener {
            startActivityForResult(
                Intent(requireContext(), AddProductActivity::class.java),
                100
            )
        }
        //activity!!.showToast("Fragment One")
        viewModel = ViewModelProvider(
            this, ViewModelFactory(
                DatabaseHelperImpl(
                    DatabaseBuilder.getInstance(requireContext())
                )
            )
        ).get(RoomDBViewModel::class.java)

        viewModel.getProductWithCoupon().observe(requireActivity(), {
            vatanNuGhamAdapter.setItem(it as ArrayList<ProductWithCoupon>)
        })


        //recyclerview
        layoutManager = LinearLayoutManager(requireContext())
        binding.rvVatanNuGham.layoutManager = layoutManager

        vatanNuGhamAdapter = ProductListAdapter {
//            startActivity(
//                Intent(
//                    this,
//                    VatanNuGhamDetailsActivity::class.java
//                ).putExtra(AppConstants.VATAN_GHAM_ID, it.id)
//            )
        }
        binding.rvVatanNuGham.adapter = vatanNuGhamAdapter
    }

    override fun onDetach() {
        super.onDetach()
        requireContext().showToast("Fragment one Detach")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 100) {
            //second check whether they are authorized to download
            viewModel.fetchProductWithCoupon()
        }
    }
}