package com.app.roomwithkotlincoroutine.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.roomwithkotlincoroutine.databinding.ActivityAddDemandBinding
import com.app.roomwithkotlincoroutine.db.DatabaseBuilder
import com.app.roomwithkotlincoroutine.db.DatabaseHelperImpl
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect
import com.app.roomwithkotlincoroutine.ui.adapter.SelectProductListAdapter
import com.app.roomwithkotlincoroutine.util.ViewModelFactory
import com.app.roomwithkotlincoroutine.viewmodel.RoomDBViewModel

class AddDemandActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDemandBinding
    private lateinit var selectProductListAdapter: SelectProductListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var viewModel: RoomDBViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddDemandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, ViewModelFactory(
                DatabaseHelperImpl(
                    DatabaseBuilder.getInstance(this)
                )
            )
        ).get(RoomDBViewModel::class.java)

        binding.btAddUpdate.setOnClickListener {

        }

        binding.fbAddProduct.setOnClickListener {
            startActivityForResult(Intent(this, SelectProductListActivity::class.java), 100)
        }

        //recyclerview
        layoutManager = LinearLayoutManager(this)
        binding.rvSelectedProducts.layoutManager = layoutManager

        selectProductListAdapter = SelectProductListAdapter {

        }
        binding.rvSelectedProducts.adapter = selectProductListAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 100) {
            val xyz: ArrayList<ProductMuliSelect> =
                data!!.getSerializableExtra("productlist") as ArrayList<ProductMuliSelect>
            selectProductListAdapter.setItem(xyz)
        }
    }
}