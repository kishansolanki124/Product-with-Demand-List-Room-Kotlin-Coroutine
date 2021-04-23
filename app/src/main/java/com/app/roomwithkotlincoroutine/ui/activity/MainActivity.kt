package com.app.roomwithkotlincoroutine.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.app.roomwithkotlincoroutine.R
import com.app.roomwithkotlincoroutine.databinding.ActivityMainBinding
import com.app.roomwithkotlincoroutine.ui.fragment.FragmentOneFragment
import com.app.roomwithkotlincoroutine.ui.fragment.FragmentTwoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
        switchFragment(FragmentOneFragment(), false)
    }

    private fun switchFragment(fragment: Fragment, addToBackStack: Boolean) {

        if (fragment !is FragmentOneFragment) {
            //hideNavigationButton()
        } else {
            //showNavigationButton()
        }

        mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.replace(R.id.fragmentContainer, fragment)
        if (addToBackStack) {
            mTransaction.addToBackStack(null)
        }
        mTransaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (binding.bottomNavigationView.selectedItemId != item.itemId) {
            when (item.itemId) {
                R.id.navigation_news -> {
                    switchFragment(FragmentOneFragment(), false)
                }
                R.id.navigation_opinion_poll -> {
                    switchFragment(FragmentTwoFragment(), false)
                }
                R.id.navigation_menu -> {
                    //switchFragment(MenuFragment(), false)
                }
            }
        }
        return true
    }
}