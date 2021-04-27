package com.app.roomwithkotlincoroutine.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.app.roomwithkotlincoroutine.R

class SplashActivity : AppCompatActivity() {

    private var mCountDownTime = 2000L //time in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            openHome()
        }, mCountDownTime)
    }

    private fun openHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}