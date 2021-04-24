package com.app.roomwithkotlincoroutine.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.roomwithkotlincoroutine.databinding.ActivityAddDemandBinding
import com.app.roomwithkotlincoroutine.db.DatabaseBuilder
import com.app.roomwithkotlincoroutine.db.DatabaseHelperImpl
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect
import com.app.roomwithkotlincoroutine.showToast
import com.app.roomwithkotlincoroutine.ui.adapter.SelectedProductListAdapter
import com.app.roomwithkotlincoroutine.util.ViewModelFactory
import com.app.roomwithkotlincoroutine.viewmodel.RoomDBViewModel

class AddDemandActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDemandBinding
    private lateinit var selectProductListAdapter: SelectedProductListAdapter
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
            startActivityForResult(
                Intent(this, SelectProductListActivity::class.java)
                    .putExtra("productlist", selectProductListAdapter.getList()), 100
            )
        }

        //recyclerview
        layoutManager = LinearLayoutManager(this)
        binding.rvSelectedProducts.layoutManager = layoutManager

        selectProductListAdapter = SelectedProductListAdapter(itemClick = {
            showToast(it.quantity.toString())
        },
            deleteClick = {
                selectProductListAdapter.removeItem(it)
            }, {
                setupAmounts()
            })


        binding.rvSelectedProducts.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL
            )
        )

        binding.rvSelectedProducts.adapter = selectProductListAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 100) {
            val productmenulist: ArrayList<ProductMuliSelect> =
                data!!.getSerializableExtra("productlist") as ArrayList<ProductMuliSelect>
            selectProductListAdapter.setItem(productmenulist)
            setupAmounts()
        }
    }

    private fun setupAmounts() {
        var amount = 0.0
        var discountAMount = 0.0
        var netAMount = 0.0
        for (item in selectProductListAdapter.getList()) {
            if (item.type.equals("none")) {
                amount += item.price
            } else if (item.type.equals("percentage")) {
                amount += item.price

                val percentageAmount = (item.price * item.amount) / 100
                discountAMount += percentageAmount

                val netPaybleAmount = item.price - percentageAmount
                netAMount += netPaybleAmount
            } else if (item.type.equals("amount")) {
                amount += item.price

                discountAMount += item.amount
                netAMount += item.price - item.amount
            }
        }

        binding.tvAmount.text = amount.toString()
        binding.tvDiscount.text = discountAMount.toString()
        binding.tvNetAmount.text = netAMount.toString()
    }
}