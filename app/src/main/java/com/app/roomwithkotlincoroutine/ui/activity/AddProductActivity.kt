package com.app.roomwithkotlincoroutine.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.roomwithkotlincoroutine.R
import com.app.roomwithkotlincoroutine.databinding.ActivityAddProductBinding
import com.app.roomwithkotlincoroutine.db.DatabaseBuilder
import com.app.roomwithkotlincoroutine.db.DatabaseHelperImpl
import com.app.roomwithkotlincoroutine.db.entity.Discount
import com.app.roomwithkotlincoroutine.util.AppConstant
import com.app.roomwithkotlincoroutine.util.ViewModelFactory
import com.app.roomwithkotlincoroutine.util.setActionNext
import com.app.roomwithkotlincoroutine.util.showToast
import com.app.roomwithkotlincoroutine.viewmodel.RoomDBViewModel

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private lateinit var viewModel: RoomDBViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, ViewModelFactory(
                DatabaseHelperImpl(
                    DatabaseBuilder.getInstance(this)
                )
            )
        ).get(RoomDBViewModel::class.java)

        binding.etDescription.setActionNext()

        binding.btAddUpdate.setOnClickListener {
            if (checkFieldsValid()) {
                viewModel.addDiscount(
                    Discount(
                        type = binding.spTNC.selectedItem.toString(),
                        amount = (if (binding.etDiscountAMount.text.toString().isNotEmpty()
                        ) binding.etDiscountAMount.text.toString().toDouble() else 0.0)
                    ),
                    binding.etTitle.text.toString(),
                    binding.etDescription.text.toString(),
                    binding.etProductPrice.text.toString().toDouble()
                )

                Handler(Looper.getMainLooper()).postDelayed({
                    setResult(AppConstant.ACTIVITY_RESULT_CODE_100)
                    finish()
                }, 500)
            }
        }

        initSpinner()
    }

    private fun initSpinner() {
        val pageList: ArrayList<String> = ArrayList()
        pageList.add(AppConstant.DiscountTYpe.NONE)
        pageList.add(AppConstant.DiscountTYpe.PERCENTAGE)
        pageList.add(AppConstant.DiscountTYpe.AMOUNT)

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            applicationContext,
            R.layout.simple_spinner_dropdown_item,
            pageList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spTNC.adapter = adapter

        binding.spTNC.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.etDiscountAMount.setText("")
                when {
                    pageList[p2] == AppConstant.DiscountTYpe.NONE -> {
                        binding.etDiscountAMount.hint = ""
                        binding.etDiscountAMount.isEnabled = false
                    }
                    pageList[p2] == AppConstant.DiscountTYpe.PERCENTAGE -> {
                        binding.etDiscountAMount.hint = getString(R.string.Discount_percentage)
                        binding.etDiscountAMount.isEnabled = true
                        binding.etDiscountAMount.filters = arrayOf(InputFilter.LengthFilter(2))
                    }
                    else -> {
                        binding.etDiscountAMount.hint = getString(R.string.Discount_Amount)
                        binding.etDiscountAMount.isEnabled = true
                        binding.etDiscountAMount.filters = arrayOf(InputFilter.LengthFilter(7))
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }
        }
    }

    private fun checkFieldsValid(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etTitle.text.toString().trim()) -> {
                showToast(getString(R.string.Enter_Title))
                false
            }
            TextUtils.isEmpty(binding.etDescription.text.toString().trim()) -> {
                showToast(getString(R.string.Enter_Desc))
                false
            }

            TextUtils.isEmpty(binding.etProductPrice.text.toString().trim()) -> {
                showToast(getString(R.string.Enter_Price))
                false
            }

            (binding.etProductPrice.text.toString().toDouble() <= 0) -> {
                showToast(getString(R.string.Invalid_Price))
                false
            }

            (binding.spTNC.selectedItem.toString() != AppConstant.DiscountTYpe.NONE && TextUtils.isEmpty(
                binding.etDiscountAMount.text.toString()
            )) -> {
                showToast(getString(R.string.Enter_Discount, binding.spTNC.selectedItem))
                false
            }

            (binding.spTNC.selectedItem.toString() == AppConstant.DiscountTYpe.AMOUNT && binding.etDiscountAMount.text.toString()
                .toDouble() >
                    binding.etProductPrice.text.toString().toDouble()) -> {
                showToast(getString(R.string.Invalid_Discount))
                false
            }

            (binding.spTNC.selectedItem.toString() != AppConstant.DiscountTYpe.NONE && binding.etDiscountAMount.text.toString()
                .toDouble() <= 0) -> {
                showToast(getString(R.string.Invalid_Discount_2))
                false
            }

            else -> {
                true
            }
        }
    }
}