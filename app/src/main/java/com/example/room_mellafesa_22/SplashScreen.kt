package com.example.room_mellafesa_22

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler



class SplashScreen : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            val intent = Intent (this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
    }
