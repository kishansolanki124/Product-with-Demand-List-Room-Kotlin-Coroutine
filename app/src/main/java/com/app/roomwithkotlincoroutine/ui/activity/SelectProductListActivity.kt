package com.app.roomwithkotlincoroutine.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.roomwithkotlincoroutine.databinding.ActivitySelectProductBinding
import com.app.roomwithkotlincoroutine.db.DatabaseBuilder
import com.app.roomwithkotlincoroutine.db.DatabaseHelperImpl
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect
import com.app.roomwithkotlincoroutine.toMultiSelect
import com.app.roomwithkotlincoroutine.ui.adapter.SelectProductListAdapter
import com.app.roomwithkotlincoroutine.util.ViewModelFactory
import com.app.roomwithkotlincoroutine.viewmodel.RoomDBViewModel


class SelectProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectProductBinding
    private lateinit var viewModel: RoomDBViewModel
    private lateinit var selectProductListAdapter: SelectProductListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            resultIntent.putExtra("productlist", selectProductListAdapter.getList())
            setResult(100, resultIntent)
            finish()
        }

        viewModel.getProductWithCoupon().observe(this, {

            val multiSelectList = ArrayList<ProductMuliSelect>()
            for (item in it) {
                multiSelectList.add(item.toMultiSelect())
            }
            selectProductListAdapter.setItem(multiSelectList)
        })

        //recyclerview
        layoutManager = LinearLayoutManager(this)
        binding.rvSelectProduct.layoutManager = layoutManager

        selectProductListAdapter = SelectProductListAdapter {

        }
        binding.rvSelectProduct.adapter = selectProductListAdapter
    }
}