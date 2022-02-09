package com.mynasmah.mykamus.ui.view.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

import com.mynasmah.mykamus.R
import com.mynasmah.mykamus.ui.view.activity.MainActivity

class ActivtitySplashscreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()

        }, 1500)
    }
}
