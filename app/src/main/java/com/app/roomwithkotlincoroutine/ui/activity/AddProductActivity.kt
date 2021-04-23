package com.app.roomwithkotlincoroutine.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.roomwithkotlincoroutine.R
import com.app.roomwithkotlincoroutine.databinding.ActivityAddProductBinding
import com.app.roomwithkotlincoroutine.databinding.ActivityMainBinding
import com.app.roomwithkotlincoroutine.db.DatabaseBuilder
import com.app.roomwithkotlincoroutine.db.DatabaseHelperImpl
import com.app.roomwithkotlincoroutine.db.Product
import com.app.roomwithkotlincoroutine.showToast
import com.app.roomwithkotlincoroutine.util.ViewModelFactory
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

        binding.btAddUpdate.setOnClickListener {
            if (checkFieldsValid()) {
                viewModel.addUser(
                    Product(
                        name = binding.etTitle.text.toString(),
                        email = binding.etDescription.text.toString(),
                        avatar = ""
                    )
                )

                setResult(100)
                finish()
            }
        }

        initSpinner()
    }

    private fun initSpinner() {
        val pageList: ArrayList<String> = ArrayList()
        pageList.add("none")
        pageList.add("percentage")
        pageList.add("fix")

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            applicationContext,
            R.layout.simple_spinner_dropdown_item,
            pageList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spTNC.adapter = adapter

        binding.spTNC.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (pageList[p2] == "none") {

                } else {

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }
        }
    }

    private fun checkFieldsValid(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etTitle.text.toString()) -> {
                showToast("add title")
                false
            }
            TextUtils.isEmpty(binding.etDescription.text.toString()) -> {
                showToast("add description")
                false
            }
            TextUtils.isEmpty(binding.etDiscountAMount.text.toString()) -> {
                showToast("add Discount Amount")
                false
            }
            else -> {
                true
            }
        }
    }
}