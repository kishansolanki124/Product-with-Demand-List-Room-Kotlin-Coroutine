package com.app.roomwithkotlincoroutine.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.roomwithkotlincoroutine.R
import com.app.roomwithkotlincoroutine.databinding.ActivityAddDemandBinding
import com.app.roomwithkotlincoroutine.db.DatabaseBuilder
import com.app.roomwithkotlincoroutine.db.DatabaseHelperImpl
import com.app.roomwithkotlincoroutine.db.entity.Demand
import com.app.roomwithkotlincoroutine.db.pojo.DemandWithProduct
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect
import com.app.roomwithkotlincoroutine.ui.adapter.SelectedProductListAdapter
import com.app.roomwithkotlincoroutine.util.AppConstant
import com.app.roomwithkotlincoroutine.util.ViewModelFactory
import com.app.roomwithkotlincoroutine.util.setRecyclerViewLayoutManager
import com.app.roomwithkotlincoroutine.util.showSnackBar
import com.app.roomwithkotlincoroutine.viewmodel.RoomDBViewModel
import java.util.*

class AddDemandActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDemandBinding
    private lateinit var selectProductListAdapter: SelectedProductListAdapter
    private lateinit var viewModel: RoomDBViewModel
    private var demandWithProduct: DemandWithProduct? = null

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

        viewModel.getDemandTransactionList().observe(this, {
            val productMultiSelectList = ArrayList<ProductMuliSelect>()
            for (item in it) {
                productMultiSelectList.add(
                    ProductMuliSelect(
                        name = item.name,
                        description = item.description,
                        type = item.type,
                        discountId = item.discountId,
                        amount = item.amount,
                        quantity = item.quantity,
                        price = item.price,
                        id = item.productId,
                        selected = true,
                    )
                )
            }

            selectProductListAdapter.setItem(productMultiSelectList)
            if (selectProductListAdapter.getList().isEmpty()) {
                binding.tvProductListEmpty.visibility = View.VISIBLE
                binding.rvSelectedProducts.visibility = View.GONE
            } else {
                binding.tvProductListEmpty.visibility = View.GONE
                binding.rvSelectedProducts.visibility = View.VISIBLE
            }
        })

        binding.btAddUpdate.setOnClickListener {
            if (areFieldsValid()) {
                if (null != demandWithProduct) {
                    viewModel.updateDemand(
                        Demand(
                            id = demandWithProduct!!.id,
                            name = binding.etPartyName.text.toString(),
                            status = AppConstant.DemandStatus.IN_PROGRESS,
                            totalAmount = binding.tvAmount.text.toString().toDouble(),
                            totalDiscount = binding.tvDiscount.text.toString().toDouble(),
                            netAmount = binding.tvNetAmount.text.toString().toDouble(),
                            createdDate = demandWithProduct!!.created_date,
                            updatedDate = Calendar.getInstance().timeInMillis
                        ),
                        selectProductListAdapter.getList()
                    )
                } else {
                    viewModel.addDemand(
                        Demand(
                            name = binding.etPartyName.text.toString(),
                            status = AppConstant.DemandStatus.IN_PROGRESS,
                            totalAmount = binding.tvAmount.text.toString().toDouble(),
                            totalDiscount = binding.tvDiscount.text.toString().toDouble(),
                            netAmount = binding.tvNetAmount.text.toString().toDouble(),
                            createdDate = Calendar.getInstance().timeInMillis,
                            updatedDate = Calendar.getInstance().timeInMillis
                        ),
                        selectProductListAdapter.getList()
                    )
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    setResult(AppConstant.ACTIVITY_RESULT_CODE_100)
                    finish()
                }, 500)
            }
        }

        binding.fbAddProduct.setOnClickListener {
            startActivityForResult(
                Intent(this, SelectProductListActivity::class.java)
                    .putExtra(AppConstant.PRODUCT_LIST, selectProductListAdapter.getList()),
                AppConstant.ACTIVITY_RESULT_CODE_100
            )
        }

        setRecyclerViewLayoutManager(binding.rvSelectedProducts)

        selectProductListAdapter = SelectedProductListAdapter(itemClick = {
        },
            deleteClick = {
                selectProductListAdapter.removeItem(it)
                setupAmounts()
            }, updateTotal = {
                setupAmounts()
            })

        binding.rvSelectedProducts.adapter = selectProductListAdapter

        if (null != intent.getSerializableExtra(AppConstant.ADD_DEMAND)) {
            demandWithProduct =
                intent.getSerializableExtra(AppConstant.ADD_DEMAND) as DemandWithProduct
            setupDemand()
        }
    }

    private fun setupDemand() {
        binding.etPartyName.setText(demandWithProduct!!.name)
        binding.tvAmount.text = demandWithProduct!!.total_amount.toString()
        binding.tvDiscount.text = demandWithProduct!!.total_discount.toString()
        binding.tvNetAmount.text = demandWithProduct!!.net_amount.toString()

        viewModel.findAllByDemandId(demandWithProduct!!.id)
    }

    private fun areFieldsValid(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etPartyName.text.toString().trim()) -> {
                showSnackBar(getString(R.string.Enter_Name))
                false
            }
            selectProductListAdapter.getList().isEmpty() -> {
                showSnackBar(getString(R.string.select_one_product_at_least))
                false
            }
            else -> {
                true
            }
        }


    }

    private fun setupAmounts() {
        var amount = 0.0
        var discountAMount = 0.0
        var netAMount = 0.0
        for (item in selectProductListAdapter.getList()) {
            when {
                item.type.equals(AppConstant.DiscountTYpe.NONE) -> {
                    amount += (item.price * item.quantity)
                    netAMount += (item.price * item.quantity)
                }
                item.type.equals(AppConstant.DiscountTYpe.PERCENTAGE) -> {
                    amount += (item.price * item.quantity)

                    val percentageAmount = (item.price * item.amount) / 100
                    discountAMount += (percentageAmount * item.quantity)

                    val netPayableAmount = item.price - percentageAmount
                    netAMount += (netPayableAmount * item.quantity)
                }
                item.type.equals(AppConstant.DiscountTYpe.AMOUNT) -> {
                    amount += (item.price * item.quantity)

                    discountAMount += (item.amount * item.quantity)
                    netAMount += ((item.price - item.amount) * item.quantity)
                }
            }
        }

        binding.tvAmount.text = amount.toString()
        binding.tvDiscount.text = discountAMount.toString()
        binding.tvNetAmount.text = netAMount.toString()

        if (selectProductListAdapter.getList().isEmpty()) {
            binding.tvProductListEmpty.visibility = View.VISIBLE
            binding.rvSelectedProducts.visibility = View.GONE
        } else {
            binding.tvProductListEmpty.visibility = View.GONE
            binding.rvSelectedProducts.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppConstant.ACTIVITY_RESULT_CODE_100) {
            val productMenuList: ArrayList<ProductMuliSelect> =
                data!!.getSerializableExtra(AppConstant.PRODUCT_LIST) as ArrayList<ProductMuliSelect>
            selectProductListAdapter.setItem(productMenuList)
            setupAmounts()
        }
    }
}