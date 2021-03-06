package com.app.roomwithkotlincoroutine.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.roomwithkotlincoroutine.databinding.ActivitySelectProductBinding
import com.app.roomwithkotlincoroutine.db.DatabaseBuilder
import com.app.roomwithkotlincoroutine.db.DatabaseHelperImpl
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect
import com.app.roomwithkotlincoroutine.ui.adapter.SelectProductListAdapter
import com.app.roomwithkotlincoroutine.util.AppConstant
import com.app.roomwithkotlincoroutine.util.ViewModelFactory
import com.app.roomwithkotlincoroutine.util.setRecyclerViewLayoutManager
import com.app.roomwithkotlincoroutine.util.toMultiSelect
import com.app.roomwithkotlincoroutine.viewmodel.RoomDBViewModel

class SelectProductListActivity : AppCompatActivity() {

    private lateinit var productmenulist: ArrayList<ProductMuliSelect>
    private lateinit var binding: ActivitySelectProductBinding
    private lateinit var viewModel: RoomDBViewModel
    private lateinit var selectProductListAdapter: SelectProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productmenulist =
            intent.getSerializableExtra(AppConstant.PRODUCT_LIST) as ArrayList<ProductMuliSelect>

        viewModel = ViewModelProvider(
            this, ViewModelFactory(
                DatabaseHelperImpl(
                    DatabaseBuilder.getInstance(this)
                )
            )
        ).get(RoomDBViewModel::class.java)

        binding.btSaveSelection.setOnClickListener {
            println(selectProductListAdapter.getList())
            val resultIntent = Intent()
            resultIntent.putExtra(AppConstant.PRODUCT_LIST, selectProductListAdapter.getList())
            setResult(AppConstant.ACTIVITY_RESULT_CODE_100, resultIntent)
            finish()
        }

        viewModel.getProductWithCoupon().observe(this, {
            val multiSelectList = ArrayList<ProductMuliSelect>()
            multiSelectList.addAll(productmenulist)
            for (item in it) {
                if (!multiSelectList.any { item2 -> item2.id == item.id }) {
                    multiSelectList.add(item.toMultiSelect())
                }
            }

            selectProductListAdapter.setItem(multiSelectList)
        })

        setRecyclerViewLayoutManager(binding.rvSelectProduct)

        selectProductListAdapter = SelectProductListAdapter {

        }
        binding.rvSelectProduct.adapter = selectProductListAdapter
    }
}