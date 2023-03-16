package com.proid.connect.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.proid.connect.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val alpha = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.alpha)
        var splashTitle : LinearLayoutCompat = findViewById(R.id.splashTitle)
        splashTitle.animation=alpha
        var pref = getSharedPreferences("login", MODE_PRIVATE)
        var isLogin = pref.getBoolean("isLogin",false)


        Handler().postDelayed(Runnable {
            if (isLogin) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java));
            } else{
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java));
            }
            finish()

        },4000)
    }
}