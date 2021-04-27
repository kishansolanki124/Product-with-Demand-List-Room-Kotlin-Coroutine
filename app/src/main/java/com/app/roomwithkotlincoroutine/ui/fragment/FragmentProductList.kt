package com.app.roomwithkotlincoroutine.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.roomwithkotlincoroutine.databinding.FragmentProductListBinding
import com.app.roomwithkotlincoroutine.db.DatabaseBuilder
import com.app.roomwithkotlincoroutine.db.DatabaseHelperImpl
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon
import com.app.roomwithkotlincoroutine.ui.activity.AddProductActivity
import com.app.roomwithkotlincoroutine.ui.adapter.ProductListAdapter
import com.app.roomwithkotlincoroutine.util.AppConstant
import com.app.roomwithkotlincoroutine.util.ViewModelFactory
import com.app.roomwithkotlincoroutine.util.setRecyclerViewLayoutManager
import com.app.roomwithkotlincoroutine.viewmodel.RoomDBViewModel

class FragmentProductList : Fragment() {

    private lateinit var binding: FragmentProductListBinding
    private lateinit var viewModel: RoomDBViewModel
    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this, ViewModelFactory(
                DatabaseHelperImpl(
                    DatabaseBuilder.getInstance(requireContext())
                )
            )
        ).get(RoomDBViewModel::class.java)

        viewModel.getProductWithCoupon().observe(requireActivity(), {
            productListAdapter.setItem(it as ArrayList<ProductWithCoupon>)
        })

        binding.fbAdd.setOnClickListener {
            startActivityForResult(
                Intent(requireContext(), AddProductActivity::class.java),
                AppConstant.ACTIVITY_RESULT_CODE_100
            )
        }

        setRecyclerViewLayoutManager(binding.rvProducts)

        productListAdapter = ProductListAdapter {

        }
        binding.rvProducts.adapter = productListAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppConstant.ACTIVITY_RESULT_CODE_100) {
            viewModel.fetchProductWithCoupon()
        }
    }
}