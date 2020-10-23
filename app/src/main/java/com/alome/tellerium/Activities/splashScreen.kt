package com.alome.tellerium.Activities

import android.content.Intent
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alome.tellerium.R
import com.alome.tellerium.Utils.AppPref
import com.alome.tellerium.Utils.Helper

 class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        FullScreencall()
        Handler().postDelayed({
            if (AppPref.isAppIntroShown) {
                val helper = Helper(this)
                if (helper.getLoggedInUser() != null) {
                    startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@SplashScreen, Login::class.java))
                    finish()
                }
            } else {
                Onboarding.startActivity(this)
                finish()
            }}, 1500)
    }


    fun FullScreencall() {
        if (VERSION.SDK_INT >= 19) {
            val v = window.decorView
            v.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }



}
