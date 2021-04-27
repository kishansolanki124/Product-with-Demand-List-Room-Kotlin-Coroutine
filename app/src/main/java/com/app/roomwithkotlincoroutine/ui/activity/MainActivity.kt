package com.app.roomwithkotlincoroutine.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.roomwithkotlincoroutine.R
import com.app.roomwithkotlincoroutine.databinding.ActivityMainBinding
import com.app.roomwithkotlincoroutine.ui.fragment.FragmentDemandList
import com.app.roomwithkotlincoroutine.ui.fragment.FragmentProductList
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val fragmentProductList = FragmentProductList()
    private val fragmentDemandList = FragmentDemandList()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = fragmentProductList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.Product)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)

        initFragments()
    }

    private fun initFragments() {
        fragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, fragmentProductList, getString(R.string.Product)).hide(
                fragmentProductList
            )
            add(R.id.fragmentContainer, fragmentDemandList, getString(R.string.Demand)).hide(
                fragmentDemandList
            )
        }.commit()
        switchFragment(fragmentProductList)
    }

    private fun switchFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().hide(activeFragment)
            .show(fragment).commit()
        activeFragment = fragment
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (binding.bottomNavigationView.selectedItemId != item.itemId) {
            when (item.itemId) {
                R.id.navProduct -> {
                    title = getString(R.string.Product)
                    switchFragment(fragmentProductList)
                }
                R.id.navDemand -> {
                    title = getString(R.string.Demand)
                    switchFragment(fragmentDemandList)
                }
            }
        }
        return true
    }
}